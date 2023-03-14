package com.edu.springshop.shop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Member;
import com.edu.springshop.model.member.MemberService;
import com.edu.springshop.sns.GoogleLogin;
import com.edu.springshop.sns.GoogleOAuthToken;
import com.edu.springshop.sns.KakaoLogin;
import com.edu.springshop.sns.KakaoOAuthToken;
import com.edu.springshop.sns.NaverLogin;
import com.edu.springshop.sns.NaverOAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//회원관 관련된 요청을 처리하는 하위 컨트롤러 
@Controller
public class MemberController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MemberService memberService;

	@Autowired
	private GoogleLogin googleLogin;
	
	@Autowired
	private KakaoLogin kakaoLogin;
	
	@Autowired
	private NaverLogin naverLogin;

	// 회원가입 폼 요청처리
	@GetMapping("/member/joinform")
	public ModelAndView getJoinForm(HttpServletRequest request) {

		return new ModelAndView("shop/member/joinform");
	}

	// 로그인 폼 요청처리
	@GetMapping("/member/loginform")
	public ModelAndView getLoginForm(HttpServletRequest request) {

		return new ModelAndView("shop/member/loginform");
	}

	// 회원가입 요청 처리
	// HttpServletRequest를 넣어야 하는이유? AOP적용을 위한 CategoryAdvice 코드에
	// 요청을 낚아채어, request 를 사용중이므로...
	@PostMapping("/member/regist")
	public ModelAndView regist(HttpServletRequest request, Member member) {
		// 3단계:
		memberService.regist(member);
		ModelAndView mav = new ModelAndView("redirect:/member/loginform");
		return mav;
	}

	// 상담게시판 페이지 요청 처리
	@GetMapping("/member/chatform")
	public ModelAndView getChatForm(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("shop/member/chat");
		return mav;
	}

	@GetMapping("/member/authform/google")
	public ModelAndView getAuthForm(HttpServletRequest request) {
		String url = googleLogin.getGrantUrl();

		ModelAndView mav = new ModelAndView("shop/member/loginform");
		mav.addObject("url", url);

		return mav;
	}

	/*
	 * 중요! 1) 토큰을 이용하여 사용자의 정보를 접근한다 Google 객체를 얻으면 됨..(Connection 객체로 얻어온다) 2) 토큰을
	 * 통해 얻은 사용자 정보를 Member DTO에 담는다 3) session에 DTO 담아서 로그인 처리를 해준다.. 4) 로그인 후 보게 될
	 * 페이지를 보여준다..(메인)
	 */
	
	// 구글 로그인 콜백
	@GetMapping("/sns/google/callback")
	public ModelAndView googlecallback(HttpServletRequest request, HttpSession session) { // 둘다 가능함!
		String code = request.getParameter("code");
		logger.info("구글에서 발급된 코드는" + code);

		// 구글이 넘겨준 code와 내 계정이 보유한 client_id 및 client_secret 를 조합하여
		// 구글 서버측에 token 발급을 요청해야하 한다..(POST) head + body 를 구성하여 요청을 시도
		// 이때 우리 스프링 서버는 상대적으로 클라이언트가 된다..
		// token은 회원 정보에 접근 할 수 있는 증명서 같은 개념..

		/*-------------------------------------------------------------
		 	1) 토큰 취득을 위한 POST 헤더와 바디 구성하기
		---------------------------------------------------------------*/
		String url = googleLogin.getToken_request_url();

		// body의 파라미터 구성하기 <파라미터명, 파라미터값>
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", googleLogin.getClient_id());
		params.add("client_secret", googleLogin.getClient_secret());
		params.add("redirect_uri", googleLogin.getRedirect_uri());
		params.add("grant_type", googleLogin.getGrant_type());

		// post 방식의 헤더(application/x-www-form-urlencoded)
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-Type", "application/x-www-form-urlencoded");

		// 머리와 몸 합치기
		HttpEntity httpEntity = new HttpEntity(params, headers);

		// 요청시도를 위한 객체생성, 비동기방식의 요청을 위한 객체
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

		/*-------------------------------------------------------------
		2) 토큰 요청후 ResponseEntity로부터 토큰 꺼내기(String에 불과하므로..)
		---------------------------------------------------------------*/
		String body = responseEntity.getBody(); // 몸체 내용 가져오기
		logger.info("구글에서 넘겨받은 응답정보" + body);

		// json 으로 되어 있는 문자열을 파싱하여, 자바의 객체로 옮겨담자
		ObjectMapper objectMapper = new ObjectMapper();
		GoogleOAuthToken oAuthToken = null;

		try { // String이 자동으로 들어와짐!
			oAuthToken = objectMapper.readValue(body, GoogleOAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		/*-------------------------------------------------------------
		3) 토큰 이용하여 회원정보 접근 / oAuthToken 안의 토큰을 이용하여 회원정보에 접근
		---------------------------------------------------------------*/
		String userinfo_url = googleLogin.getUserinfo_url();

		// GET 방식 요청 구성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
		HttpEntity entity = new HttpEntity(headers2);
		
		// 비동기 객체를 이용한 GET 요청
		RestTemplate restTemplate2 = new RestTemplate();
		ResponseEntity<String> userEntity = restTemplate2.exchange(userinfo_url, HttpMethod.GET, entity, String.class);

		String userBody = userEntity.getBody();
		logger.info(userBody);
		HashMap<String, Object> userMap = new HashMap<String, Object>();

		// 사용자 정보 추출하기
		ObjectMapper objectMapper2 = new ObjectMapper(); // 코드 나중에 다듬기!
		try {
			userMap = objectMapper2.readValue(userBody, HashMap.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		String id = (String) userMap.get("id");
		String email = (String) userMap.get("email");
		boolean verified_email = (Boolean) userMap.get("verified_email");
		String nikname = (String) userMap.get("name");

		logger.info("id" + id);
		logger.info("email" + email);
		logger.info("verified_email" + verified_email);
		logger.info("nikname" + nikname);

		if(false) {
		// 이미 db에 회원의 식별고유 id 가 존재할 경우
		// 회원가입을 처리해주자 (서비스의 regist/세션에 담자)
		}else {
		// 그렇지 않은 경우 로그인 처리만 하자(세션에 담자)
		}
		
		ModelAndView mav= new ModelAndView("redirect:/");

		return mav;
	}
	
	//카카오 로그인 콜백
		@GetMapping("/sns/kakao/callback")
		public ModelAndView kakaoCallback(HttpServletRequest request, HttpSession session) {
			String code = request.getParameter("code");
			logger.info("카카오에서 발급된 코드는 "+code);
			
			/*-----------------------------------------------------------------
			 1) 토큰 취득을 위한 POST 헤더와 바디 구성하기 
			-----------------------------------------------------------------*/
			
			String url=kakaoLogin.getToken_request_url();
			
			//body 의 파라미터 구성하기  <파라미터명, 파라미터값>
			MultiValueMap<String, String> params=new LinkedMultiValueMap<String, String>();
			params.add("code", code);
			params.add("client_id", kakaoLogin.getClient_id());
			params.add("redirect_uri", kakaoLogin.getRedirect_uri());
			params.add("grant_type", kakaoLogin.getGrant_type());
			
			//post 방식의 헤더 (application/x-www-form-urlencoded)
			HttpHeaders headers=new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			
			//머리와 몸 합치기 
			HttpEntity httpEntity=new HttpEntity(params, headers);
			
			//요청시도를 위한 객체생성, 비동기방식의 요청을 위한 객체
			RestTemplate restTemplate=new RestTemplate();
			ResponseEntity<String> responseEntity=restTemplate.exchange(url,HttpMethod.POST, httpEntity, String.class);
			
			/*-----------------------------------------------------------------
			 2) 토큰 요청후  ResponseEntity로부터 토큰 꺼내기 (String에 불과하므로..)
			-----------------------------------------------------------------*/
			String body=responseEntity.getBody();
			logger.info("카카오에서 넘겨받은 응답정보"+body);
			
			//json 으로 되어있는 문자열을 파싱하여, 자바의 객체로 옮겨담자
			ObjectMapper objectMapper=new ObjectMapper();
			KakaoOAuthToken oAuthToken=null;
			
			try {
				oAuthToken=objectMapper.readValue(body, KakaoOAuthToken.class);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			// oAuthToken 안의 토큰을 이용하여 회원정보에 접근		
			/*-----------------------------------------------------------------
			 3) 토큰을 이용하여 회원정보에 접근
			-----------------------------------------------------------------*/
			
			String userinfo_url=kakaoLogin.getUserinfo_url();
			
			//GET방식요청 구성 
			HttpHeaders headers2 = new HttpHeaders();
			headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
			HttpEntity entity=new HttpEntity(headers2);
			
			//비동기객체를 이용한 GET  요청
			RestTemplate restTemplate2 = new RestTemplate();
			ResponseEntity<String> userEntity=restTemplate2.exchange(userinfo_url, HttpMethod.GET, entity, String.class);
			
			String userBody = userEntity.getBody();
			logger.info(userBody);
			
			HashMap<String, Object> userMap=new HashMap<String, Object>();
			
			//사용자 정보 추출하기 
			ObjectMapper objectMapper2 = new ObjectMapper();
			try {
				userMap=objectMapper2.readValue(userBody, HashMap.class);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			//String id=(String)userMap.get("id");
			//String connected_at =(String)userMap.get("conntected_at");
			/*
			String id = (String) userMap.get("id");
			String email = (String) userMap.get("email");
			boolean verified_email = (Boolean) userMap.get("verified_email");
			String nikname = (String) userMap.get("name");

			
			logger.info("id" + id);
			logger.info("email" + email);
			logger.info("verified_email" + verified_email);
			logger.info("nikname" + nikname);
			*/
			
//			String id=Integer.toString(()userMap.get("id"));
//			String connected_at=(String)userMap.get("connected_at");
			
			
			//내부의 json은 맵으로 처리
			Map properties =(Map)userMap.get("properties");
			String nickname=(String)properties.get("nickname");
			
			logger.info("id is "+userMap.get("id"));
			logger.info("nickname is "+nickname);
			
			if(false) {
				//이미 db이 이 회원의 식별고유 id 가 존재할 경우
				//회원가입을 처리해주자 (서비스의 regist) 세션에 담자 
			}else {
				//그렇지 않은경우
				//로그인 처리만 하자 (세션에 담자)
			}
			
			ModelAndView mav = new ModelAndView("redirect:/");
			
			return mav;
		}
	
	// 네이버 로그인 콜백
		@GetMapping("/sns/naver/callback")
		public ModelAndView navercallback(HttpServletRequest request, HttpSession session) { // 둘다 가능함!
			String code = request.getParameter("code");
			logger.info("Naver에서 발급된 코드는" + code);

			/*-------------------------------------------------------------
			 	1) 토큰 취득을 위한 POST 헤더와 바디 구성하기 ( 웹서버에서 클라이언트가 되는 과정)
			---------------------------------------------------------------*/
		
			String url = naverLogin.getToken_request_url();

			// body의 파라미터 구성하기 <파라미터명, 파라미터값>
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("code", code);
			params.add("client_id", naverLogin.getClient_id());
			params.add("client_secret", naverLogin.getClient_secret());
			params.add("redirect_uri", naverLogin.getRedirect_uri());
			params.add("grant_type", naverLogin.getGrant_type());
			params.add("state", naverLogin.getState());
			
			// post 방식의 헤더(application/x-www-form-urlencoded)
			HttpHeaders headers = new HttpHeaders();
			headers.add("content-Type", "application/x-www-form-urlencoded");

			// 머리와 몸 합치기
			HttpEntity httpEntity = new HttpEntity(params, headers);

			// 요청시도를 위한 객체생성, 비동기방식의 요청을 위한 객체
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

			/*-------------------------------------------------------------
			2) 토큰 요청후 ResponseEntity로부터 토큰 꺼내기(String에 불과하므로..)
			---------------------------------------------------------------*/
			String body = responseEntity.getBody(); // 몸체 내용 가져오기
			logger.info("네이버에서 넘겨받은 응답정보" + body);

			
			// json 으로 되어 있는 문자열을 파싱하여, 자바의 객체로 옮겨담자
			ObjectMapper objectMapper = new ObjectMapper();
			NaverOAuthToken oAuthToken = null;

			try { // String이 자동으로 들어와짐!
				oAuthToken = objectMapper.readValue(body, NaverOAuthToken.class);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			// oAuthToken 안의 토큰을 이용하여 회원정보에 접근
			/*-------------------------------------------------------------
			3) 토큰 이용하여 회원정보 접근
			---------------------------------------------------------------*/
			String userinfo_url = naverLogin.getUserinfo_url();

			// GET 방식 요청 구성
			HttpHeaders headers2 = new HttpHeaders();
			headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
			HttpEntity entity = new HttpEntity(headers2);
			
			// 비동기 객체를 이용한 GET 요청
			RestTemplate restTemplate2 = new RestTemplate();
			ResponseEntity<String> userEntity = restTemplate2.exchange(userinfo_url, HttpMethod.GET, entity, String.class);

			String userBody = userEntity.getBody();
			logger.info("회원정보는"+userBody);
			
			HashMap<String, Object> userMap = new HashMap<String, Object>();

			// 사용자 정보 추출하기
			ObjectMapper objectMapper2 = new ObjectMapper(); // 코드 나중에 다듬기!
			try {
				userMap = objectMapper2.readValue(userBody, HashMap.class);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			
			//String id=Integer.toString(()userMap.get("id"));
			//String connected_at=(String)userMap.get("connected_at");
			
			//내부의 json은 맵으로 처리
			Map response=(Map)userMap.get("response");

			String id=(String)response.get("id");
			String nickname=(String)response.get("nickname");
			String gender=(String)response.get("gender");
			String email=(String)response.get("email");
			String moblie=(String)response.get("moblie");
			
			logger.info("id is"+id); //나중에 member DTO에 담기!!
			logger.info("nickname is"+nickname);
			logger.info("gender is"+gender);
			logger.info("email is"+email);
			logger.info("mobile is"+moblie);
			
			if(false) {
			// 이미 db에 회원의 식별고유 id 가 존재할 경우
			// 회원가입을 처리해주자 (서비스의 regist/세션에 담자)
			}else {
			// 그렇지 않은 경우 로그인 처리만 하자(세션에 담자)
			}
			
			ModelAndView mav= new ModelAndView("redirect:/");

			return mav;
		}
		
}
