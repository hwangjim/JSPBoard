<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<%--로그인한 아이디가 null 값이 아니라면 즉, 로그인한상태에선 무조건 --%>
<c:choose>
	<c:when test="${userId != null}">
		<%--로그아웃 태그가 뜨게끔
		태그를 실제로 누르면 logout.do로 이동 --%>
		<a href="logout.do">로그 아웃</a>
	</c:when>
	<c:otherwise>
	<%--submit을 누르면 login.do로 post방식 활용해서 가게끔 --%>
	<form action="login.do" method="post">
		ID&nbsp;<input type="text" name="mid">&nbsp;&nbsp;PW&nbsp;<input type="password" name="mpw">&nbsp;&nbsp;
		<input type="submit" value="로그인">
	</form>
	</c:otherwise>
</c:choose>