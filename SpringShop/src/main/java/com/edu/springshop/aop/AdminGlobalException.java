package com.edu.springshop.aop;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.exception.AdminException;

@ControllerAdvice
public class AdminGlobalException {
	
	
	@ExceptionHandler(AdminException.class)
	public ModelAndView handle(AdminException e) {
		ModelAndView mav= new ModelAndView("admin/error/result");
		mav.addObject("e", e);
		
		return mav;
	} 
}
