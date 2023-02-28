package com.edu.springshop.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Admin;
import com.edu.springshop.exception.AdminException;

@Controller
public class AdminController {
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/loginform")
	public ModelAndView getLoginForm(HttpServletRequest request) {
		ModelAndView mav= new ModelAndView("admin/login/loginform");
		
		return mav;
	}
	
	@GetMapping("/main")
	public ModelAndView getMain(HttpServletRequest request) {
		logger.info("관리자메인 호출됨");
		
		//로그인 인증여부를 따져봐야 한다...
		HttpSession session= request.getSession();
		Admin admin=(Admin)session.getAttribute("admin");
		
		ModelAndView mav= new ModelAndView();
		
		if(admin==null) {
//			mav.setViewName("admin/error/result"); //에러 페이지
//			mav.addObject("",);
			throw new AdminException("로그인이 필요한 서비스입니다.");
		}else {
			mav.setViewName("admin/index"); //정상링크
		}
		
		
		return mav;
	}
	/*
	  @ExceptionHandler(AdminException.class) public ModelAndView
	  handle(AdminException e) { ModelAndView mav= new
	  ModelAndView("admin/error/result"); mav.addObject("e", e);
	  
	  return mav; }
	 */
	
}















