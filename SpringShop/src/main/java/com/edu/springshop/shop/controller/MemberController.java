package com.edu.springshop.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Member;
import com.edu.springshop.model.member.MemberService;
import com.edu.springshop.sns.GoogleLogin;

//회원관 관련된 요청을 처리하는 하위 컨트롤러 
@Controller
public class MemberController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private GoogleLogin googleLogin;
	
	//회원가입 폼 요청처리
	@GetMapping("/member/joinform")
	public ModelAndView getJoinForm(HttpServletRequest request) {
		
		return new ModelAndView("shop/member/joinform");
	}
	
	//로그인 폼 요청처리
	@GetMapping("/member/loginform")
	public ModelAndView getLoginForm(HttpServletRequest request) {
		
		return new ModelAndView("shop/member/loginform");
	}
	
	
	//회원가입 요청 처리 
	//HttpServletRequest를 넣어야 하는이유?  AOP적용을 위한 CategoryAdvice 코드에
	//요청을 낚아채어, request 를 사용중이므로...
	@PostMapping("/member/regist")
	public ModelAndView regist(HttpServletRequest request, Member member) {
		//3단계: 
		memberService.regist(member);
		ModelAndView mav = new ModelAndView("redirect:/member/loginform");
		return mav;
	}
	
	//상담게시판 페이지 요청 처리
	@GetMapping("/member/chatform")
	public ModelAndView getChatForm(HttpServletRequest request) {
		ModelAndView mav= new ModelAndView("shop/member/chat");
		return mav;
	}
	
	@GetMapping("/member/authform/google")
	public ModelAndView getAuthForm(HttpServletRequest request) {
		String url = googleLogin.handle();
		
		ModelAndView mav= new ModelAndView("shop/member/loginform");
		mav.addObject("url", url);
		
		return mav;
	}
	
	//구글 로그인 콜백
	@GetMapping("/member/auth/google")
	public ModelAndView callback(HttpServletRequest request, HttpSession session) { //둘다 가능함!
		String code=request.getParameter("code");
		logger.info("구글에서 발급된 코드는"+code);
		
		//코드 + clientId +clientSecret == 토큰 /토큰 발급 과정
		OAuth2Operations operation=  googleLogin.getGoogleConnectionFactory().getOAuthOperations();
		
		//넘겨받은 코드를 이용하여 권한객체를 반환받는다
		AccessGrant accessGrant=operation.exchangeForAccess(code, googleLogin.getOAuth2Parameters().getRedirectUri(), null);
		String token=accessGrant.getAccessToken(); 
		
		logger.info("발급받은 토큰은"+token);
		
		//스프링 소셜 커넥션임!
		Connection con=googleLogin.getGoogleConnectionFactory().createConnection(accessGrant);
		Google google=(Google)con.getApi(); //나머지는 조사!! 이걸통해 프로필을 얻어올수 있음!
		
		/*중요! 
		 * 1) 토큰을 이용하여 사용자의 정보를 접근한다
		 * 		Google 객체를 얻으면 됨..(Connection 객체로 얻어온다)
		 * 2) 토큰을 통해 얻은 사용자 정보를 Member DTO에 담는다
		 * 3) session에 DTO 담아서 로그인 처리를 해준다..
		 * 4) 로그인 후 보게 될 페이지를 보여준다..(메인)
		 */		
		
		return null;
	}
	
}












