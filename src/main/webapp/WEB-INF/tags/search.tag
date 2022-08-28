<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--data라는 변수명 지정 이후 item에는 member 배열 리스트 받아옴 --%>
<c:forEach var="data" items="${member}">
<%--만약 member내 mid 즉 data.mid가 null이라면 즉 
최근 가입한 회원이 존재하지 않는다면 --%>
<c:if test="${data.mid == null}">
	최근에 가입한 회원이 없습니다.
</c:if>
	<tr>
		<%--최근 가입한 회원 존재한다면 그들을 뽑아줄건데, mid 속성 값을 주고 화면상으로는 이름을 보여준다 --%>
		<th><a href="main.do?mid=${data.mid}&cnt=2&showContent=${data.mid}">[${data.mname}]&nbsp;</a></th>
	</tr>
</c:forEach>

