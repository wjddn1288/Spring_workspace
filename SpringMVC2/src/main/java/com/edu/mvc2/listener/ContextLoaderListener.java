package com.edu.mvc2.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextLoaderListener implements ServletContextListener{
	ServletContext context; 
	//JSP에서의 application 내장객체
	//application.getRealPath()에 이용했었다.
	//javaee 기반의 서버의 메모리에서 데이터를 개발자가 심을수 있는
	//객체가 3가지 있다.
	//request<session<application
	ApplicationContext application;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("서버 시작 감지");
		
		sce.getServletContext().getInitParameter("contextConfigLocation"); //web.xml context의 파라미터!
		ServletContext context=sce.getServletContext(); //내장객체 가져오기
		application=new ClassPathXmlApplicationContext("root-context.xml");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("서버 중지 감지");
	}

}
