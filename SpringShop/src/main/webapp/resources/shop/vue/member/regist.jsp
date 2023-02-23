<%@page import="com.google.gson.Gson"%>
<%@page import="com.jspshop.util.MessageObject"%>
<%@page import="com.jspshop.exception.MemberException"%>
<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@page import="com.jspshop.domain.Member"%>
<%@page import="com.jspshop.repository.MemberDAO"%>
<%@ page contentType="application/json;charset=UTF-8"%>
<%!
	MybatisConfig mybatisConfig =MybatisConfig.getInstance();
	MemberDAO memberDAO = new MemberDAO();
%>
<%
	//dao에게 주입(injection)
	SqlSession sqlSession= mybatisConfig.getSqlSession();
	memberDAO.setSqlSession(sqlSession);
	
	//파라미터 받기 전에 설정해야 한다
	request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="member" class="com.jspshop.domain.Member"/>
<jsp:setProperty property="*" name="member"/>
<%
	System.out.println(member.getId());
	System.out.println(member.getPass());
	System.out.println(member.getName());
	System.out.println(member.getEmail());

	MessageObject mo=new MessageObject();
	
	try{
		memberDAO.insert(member);
		mo.setCode(1); //채워넣기 우리만의 규칙 성공
		mo.setMsg("등록 성공"); //하고 싶은말
		sqlSession.commit(); //커밋 묶은적 없으니까 커밋만 트랜잭션 상황이 아님
	}catch(MemberException e){
		mo.setCode(0);
		mo.setMsg(e.getMessage()); //에러메시지 자동 대입
	}
	Gson gson=new Gson();
	out.print(gson.toJson(mo)); //JSON 문자열로 변환하여 결과 전송
	
	mybatisConfig.release(sqlSession);
%>