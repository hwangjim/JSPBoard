<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="type" %>
<%@ attribute name="bid" %>

 <%--로그인한 아이디가 null이 아니라면 즉, 로그인이 되어있다면 --%>
<c:if test="${userId != null}">
<c:choose>
	
	<%--글을 등록하는 form이 출력된다. 
	type이 msg라면 즉, main.jsp에서 type 속성값을 msg로 작성했다면 --%>
	<c:when test="${type=='msg'}">
	<form action="insertB.do?" method="post">
		<input type="hidden" name="mid" value="${userId}">
		<input type = "hidden" name = "cnt" value = "${cnt}">
		<input type="text" name="msg">
		<input type="submit" value="글 등록">
	</form>
	</c:when>
	
	<%--댓글을 작성하는 form이 출력된다. --%>
	<c:when test="${type=='rmsg'}">
	<form action="insertR.do?" method="post">
		<input type="hidden" name="mid" value="${userId}">
		<input type="hidden" name="bid" value="${bid}">
		<input type = "hidden" name = "cnt" value = "${cnt}"> 
		댓글 : <input type="text" name="rmsg">
		<input type="submit" value="댓글 등록">
	</form>
	</c:when>
</c:choose>
</c:if>
<%--즉 로그인했을때만 글, 댓글을 작성하게끔 만들었다. --%>

<%--로그인한 아이디가 null이라면, 즉 로그인하지 않은 비로그인 상태라면 --%>
<c:if test="${userId == null}">
<c:choose>
	<%--글, 댓글 작성 전부 못하게 막아놓는다. --%>
	<c:when test="${type=='msg'}">
		<input type="text" disabled value="등록하려면 로그인하세요!">
	</c:when>
	
	<c:when test="${type=='rmsg'}">
		댓글: <input type="text" disabled value="등록하려면 로그인하세요!">
	</c:when>
</c:choose>
</c:if>