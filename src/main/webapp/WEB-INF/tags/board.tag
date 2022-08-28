<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--속성 이름 설정(필수속성) --%>
<%@ attribute name="midCheck" %>
<%@ attribute name="bid" %>
<%--midcheck의 값과 현재 로그인한 아이디가 같은 값이라면 --%>
<%--즉 본인의 게시글 여러개중 본인의 게시글은 --%>
<c:if test="${userId==midCheck}">
	<%--본인이 삭제가 가능하다. --%>
	<%--이때 cnt는 메인 페이지에 보여줄 글의 개수로 페이징 유지를 위해 작성
	메인 페이지 내 글의 개수가 계속 유지되게끔 달아놔준것 --%>
	<a href="deleteB.do?bid=${bid}&cnt=${cnt}">[삭제]</a>
</c:if>
<%--로그인한 아이디가 null이 아니라면 --%>
<%--즉 어떤 글이든 로그인만했다면 --%>
<c:if test="${userId != null}">
	<%--좋아요 누를수있다 
	cnt역시 좋아요 개수로 페이징 유지를 위해 작성 --%>
	<a href="fav.do?bid=${bid}&cnt=${cnt}">&nbsp;♥</a>
</c:if>