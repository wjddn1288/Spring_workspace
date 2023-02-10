package com.edu.mvc2.admin.controller.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.edu.mvc2.model.board.BoardService;

import lombok.Setter;

//관리자모드의 게시판 목록요청을 처리하는 하위 컨트롤러
@Setter
public class ListController implements Controller{
	Logger logger= LoggerFactory.getLogger(this.getClass().getName());
	private BoardService boardService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//3단계 : 일 시키기
		System.out.println("관리자모드 게시판 목록 요청 받음");
		
		//3단계 : 일 시키기
		List boardList=boardService.selectAll();
		
		//4단계 : 결과저장
		ModelAndView mav=new ModelAndView("/adminboard/list");
		mav.addObject("boardList",boardList);
		return mav;
	}

}
