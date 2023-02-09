package com.edu.mvc2.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*
 * 필터 인터페이스는, 서블릿이 요청을 받기도 전에 요청을 낚아채는 메서드를 지원해준다.
 * 따라서 이 필터 인터페이스르 재정의하여, 우리만의 기능을 필터로 구현해본다..
 * 계획) 파라미터에 대한 인코딩 처리후, 서블릿에게 제어권도 넘겨줘야 함..
 * */
public class CharacterEncodingFilter implements Filter{
	String encoding;
	
	//필터 객체가 생성될때 초기화 작업 사용
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		filterConfig.getInitParameter("encoding");
	}

	//요청이 들어올때 호출됨... 즉 서블릿 보다 먼저 요청을 낚아챈다..
	//따라서 낚아챈 이후엔 다시 서블릿으로 요청이 들어가게끔 진행해줘야한다
	//서블릿은 자신이 받은 요청 이전에 어떤 필터가 동작했는지 모른다..
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);//가던길 그대로 가게해줘야한다!
		
	}

	//필터가 소멸될때...(혹시 반납할 자원이 있다면)
	@Override
	public void destroy() {
		
	}

}
