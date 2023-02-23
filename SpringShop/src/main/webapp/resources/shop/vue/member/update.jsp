<%@page import="com.jspshop.repository.MemberDAO"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%!
	MemberDAO memberDAO= new MemberDAO();
%>
<%request.setCharacterEncoding("utf-8");%>
<%
	String member_idx= request.getParameter("member_idx");
%>