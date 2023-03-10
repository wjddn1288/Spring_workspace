package com.edu.springshop.shop.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.springshop.domain.Member;
import com.edu.springshop.model.member.MemberService;
import com.edu.springshop.sns.GoogleLogin;
import com.edu.springshop.util.Message;

@RestController
@RequestMapping("/rest")
public class RestMemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private GoogleLogin googleLogin;

	// 회원가입 요청 처리
	@PostMapping("/member")
	public ResponseEntity<Message> regist(HttpServletRequest request, Member member) {
		// 3단계: 일 시키기
		memberService.regist(member);
		Message message = new Message();
		message.setMsg("회원가입 성공");

		ResponseEntity entity = new ResponseEntity<Message>(message, HttpStatus.OK);
		return entity;
	}

	/*
	 * // 구글 로그인 인증화면(사용자가 볼) 요청 처리 네이버면 뒤에 네이버로 바꿔주기!
	 * 
	 * @GetMapping("/member/authform/google") public ResponseEntity<Message>
	 * getUrl(HttpServletRequest request, Member member) { // 사용자가 보게될 인증화면에 대한 주소
	 * 구하기 String url = googleLogin.handle();
	 * 
	 * Message message = new Message(); message.setMsg(url);
	 * 
	 * ResponseEntity entity = new ResponseEntity<Message>(message, HttpStatus.OK);
	 * return entity; }
	 */

	/*
	 * @ExceptionHandler(HashException.class) public ResponseEntity<Message>
	 * handle(HashException e){
	 * 
	 * Message message = new Message(); message.setMsg("회원가입 실패");
	 * 
	 * ResponseEntity entity=new ResponseEntity<Message>(message,
	 * HttpStatus.INTERNAL_SERVER_ERROR); return entity; };
	 * 
	 * @ExceptionHandler(EmailException.class) public ResponseEntity<Message>
	 * handle(EmailException e){
	 * 
	 * Message message = new Message(); message.setMsg("회원가입 실패");
	 * 
	 * ResponseEntity entity=new ResponseEntity<Message>(message,
	 * HttpStatus.INTERNAL_SERVER_ERROR); return entity; };
	 * 
	 * @ExceptionHandler(MemberException.class) public ResponseEntity<Message>
	 * handle(MemberException e){
	 * 
	 * Message message = new Message(); message.setMsg("회원가입 실패");
	 * 
	 * ResponseEntity entity=new ResponseEntity<Message>(message,
	 * HttpStatus.INTERNAL_SERVER_ERROR); return entity; };
	 */
}
