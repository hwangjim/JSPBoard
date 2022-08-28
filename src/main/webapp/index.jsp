<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// redirect로 main.do
	response.sendRedirect("main.do");
	// ctrlB.jsp?action=main --> 이제 파라미터 하나를 덜 쓰기 때문에 가벼워진다.
%>
