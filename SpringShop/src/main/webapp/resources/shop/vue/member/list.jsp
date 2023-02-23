<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.List"%>
<%@page import="com.jspshop.repository.MemberDAO"%>
<%@ page contentType="application/json;charset=UTF-8"%>
<%! 
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	MemberDAO memberDAO = new MemberDAO();
%>
<%
	//DAO 주입
	SqlSession sqlSession= mybatisConfig.getSqlSession();
	memberDAO.setSqlSession(sqlSession);
	
	//목록가져오기
	List memberList= memberDAO.selectAll();
	Gson gson= new Gson();
	
	String jsonList=gson.toJson(memberList);
	out.print(jsonList);
	
	mybatisConfig.release(sqlSession);
%>