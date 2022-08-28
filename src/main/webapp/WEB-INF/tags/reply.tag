<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--태그 내 attribute를 설정해주고 jsp파일에서 해당 커스텀태그 활용시
자동으로 필수 속성이라 생김 그리고 속성에 해당 값을 넣어주면 그 값이 태그로 넘어와
지시어 밑의 코드들에 적용돼 활용됨 --%>
<%@ attribute name="midCheck" %>
<%@ attribute name="rid" %>
<%--session으로 로그인한 아이디가  jsp 내 커스텀태그 속성 midcheck = "" 안에 적은
값과 같을 경우 즉 본인이 쓴 글을 경우 --%>
<c:if test="${userId==midCheck}">
<%--삭제 태그가 뜬다. 역시 cnt로 페이징 유지 --%>
<a href="deleteR.do?rid=${rid}&cnt=${cnt}">[삭제]</a>

</c:if>