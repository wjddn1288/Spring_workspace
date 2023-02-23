<%@page import="com.jspshop.util.ResponseMessage"%>
<%@page import="com.jspshop.domain.Member"%>
<%@page import="com.jspshop.exception.MemberException"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.repository.MemberDAO"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	MemberDAO memberDAO= new MemberDAO(); 
	
%>
<%
	//로그인 성공 후, 사용자의 기록을 메모리에 남겨놓기 위해(회원서비스를 위함)
	//세션객체에 로그인한 결과인 DTO를 담아놓자(키 이름: member)
	//로그인 성공 후 쇼핑몰 메인으로 보여주기
	String id=request.getParameter("id");
	String pass=request.getParameter("pass");
	
	Member member=new Member();
	
	member.setId(id);
	member.setPass(pass);

	SqlSession sqlSession = mybatisConfig.getSqlSession();
	memberDAO.setSqlSession(sqlSession);
	
	try{
		Member obj=memberDAO.select(member); //위에 멤버랑 충돌나서 obj
		session.setAttribute("member", obj);//세션에 담아두기
		//메인페이지 보여주기
		out.print(ResponseMessage.getMsgURL("로그인 성공","/"));
	}catch(MemberException e){
		//뒤로 보내기
		out.print(ResponseMessage.getMsgBack(e.getMessage()));
	}finally{
		mybatisConfig.release(sqlSession);
	}
%>
	
