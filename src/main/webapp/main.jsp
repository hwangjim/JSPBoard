<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kim" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<link rel="stylesheet" href="css/main.css" type="text/css" />
<script type="text/javascript">
	// 회원가입 창을 따로 열게끔 하는 function  
	function signup(){
		window.open("signup.jsp","회원가입 페이지","width=500,height=200");
	}
	
</script>
</head>
<body>
	
	<div id="header">
		<h1>안녕맨</h1>
		<!-- 만약 userId가 null이 아니라면 
		즉, 로그인 되었다면 -->
		<c:if test = "${userId != null}">
		<!-- h3태그를 보여준다. -->
		<h3>${userId} 맨 반갑맨</h3> 
		</c:if>
		<div class="gnb">
			<ul>
				<!-- main으로 가게끔 -->
				<li><a href="main.do?showContent=main">메인으로</a></li>
				<!-- 누르면 회원가입 창이 따로 뜨게끔 function signup -->
				<li><a href="javascript:signup()">회원가입</a></li>
				<!-- 로그인 태그파일 출력 -->
				<li><kim:login /></li>
				<!-- 만약 로그인했다면 -->
				<c:if test = "${userId != null}">
				<!-- 회원가입 탈퇴창이 뜨도록 -->
             	<li><kim:deleteacc/></li>
				</c:if>
			</ul>
		</div>
		<!-- 최근 가입한 회원이 뜨게끔, 이후 이 명단은 search 태그 활용 -->
		<span>최근 가입한 시청자</span>
		<kim:search />
	</div>
	
	<div id="wrapper">
	
		<div id="content">
			<h2>글 등록하기</h2>
			<!-- write 태그 활용 -->
			<kim:write type="msg" />
		</div>
		
		<div id="main">
			<h2>글 목록보기&nbsp;&nbsp;
			
			<!-- c:if를 걸어 mid가 null이 아니면 즉 로그인했다면 -->
			<c:if test="${userId != null}">
				<!-- 내가 쓴글을 보여준다. -->
				<!-- session에 있는 내 아이디를 main.do에 보냄 
				원래 전체 게시글 가져오게 되어있음 cnt개수 만큼 
				mid로 찾는 selectAll이 필요 -> 내가 쓴 것이기 때문에 ==> pk로 구별 => 이게 없음
				=>-->
				<!-- mid가 로그인한 아이디 즉 userId일때 
				showContent를 활용해 해당 userId일때 쓴글을 보여준다-->
				<a href="main.do?mid=${userId}&showContent=${userId}">내가 쓴 글</a>
			</c:if>	
			</h2>
			<!-- c:forEach문을 사용해 datas에 v.boardVO를 각각 넣어준다. -->
			<c:forEach var="v" items="${datas}">
			<!-- v.boardVO 값에 아래 h3태그 사이에 있는 것들을 넣어준다. -->
				<c:set var="b" value="${v.boardVO}" />
				<h3>[${b.mid}] ${b.msg} [ 좋아요 ${b.favcnt} | 댓글 ${b.rcnt} ] <kim:board midCheck="${b.mid}" bid="${b.bid}" /></h3>
								
				<div class="reply">
					<ul>
						<!-- replyVO가 들어가는 rList를 v로 처리 이도 역시 datas에 넣어준다.  -->
						<c:forEach var="r" items="${v.rList}">
							<li>[${r.mid}] ${r.rmsg} <kim:reply midCheck="${r.mid}" rid="${r.rid}" /></li>
						</c:forEach>
					</ul>
				</div>
			<!-- 댓글 작성창, boardVO에서 받아온 bid로 작성됨 -->
			<div class="reply">
				<kim:write type="rmsg" bid="${b.bid}" />
			</div>
			</c:forEach>
		</div>
		<!-- 모든 게시글 보면 더보기 버튼 비활성화 즉 모든 게시글을 다 보지 않았을때-->
		<c:if test="${!noMoreContent}">
		<!-- 내가 쓴 글만 더보기 mid-->
		<!-- mid 전달 필요함 -->
		<!-- moreContent == mid가 있냐 없냐 paramMid를 넣어줬기 떄문 
		이때 moreContent가 null 값이 아니라면-->
			<c:if test="${moreContent != null}">
			<!-- 내가 쓴글이cnt가 2개 만큼 계속해서 누를때마다 증가 -->
				<a href="main.do?showContent=${moreContent}&cnt=${cnt+2}">더보기&gt;&gt;</a>
			</c:if>
			<!-- 전체 글 2개씩 더보기 cnt -->
			<!-- 전체 더 보기라서 mid 전달 필요 x  -->
			<c:if test="${moreContent == null}">
				<a href="main.do?cnt=${cnt+2}">더보기&gt;&gt;</a>
			</c:if>
		</c:if>
		
	</div>
		
	<div id="footer">
		회사소개 | 이용약관 | <strong>개인정보처리방침</strong> | 보호정책 | 고객센터 <strong>ⓒ Corp.</strong>
	</div>

</body>
</html>

