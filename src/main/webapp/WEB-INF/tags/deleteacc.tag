<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--url에 정보를 드러내지 않기 위해 post방식 사용 id, pw이니까.. --%>
<form action="deleteM.do" method="post">
						<%-- hidden으로 사용자에게 보여주지 않음 --%>
                     <input type="hidden" name="action" value="deleteM">
                     <input type="hidden" name = "mid" value = "${userId}">
                     
                     비밀번호 : <input type="password" name="mpw" placeholder = "비밀번호 확인">
                     <input type="submit" value="회원탈퇴">
                  </form>
