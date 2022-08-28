<%--
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,board.*" %>
<jsp:useBean id="bDAO" class="board.CboardDAO" />
<jsp:useBean id="bVO" class="board.CboardVO" />
<jsp:setProperty property="*" name="bVO" />
<jsp:useBean id="rVO" class="board.ReplyVO" />
<jsp:setProperty property="*" name="rVO" />
<%
String action=request.getParameter("action");
	System.out.println("action : " + action);

	String paramCnt=request.getParameter("cnt");
	if(paramCnt==null || paramCnt.equals("")){
		bVO.setCnt(2); // 향후 초기화 매개변수 등으로 설정가능
	}
//	System.out.println("cnt: "+bVO.getCnt());
//	System.out.println("mid : " + bVO.getMid());


	if(action.equals("main")){
		ArrayList<CboardSet> datas=bDAO.selectAll(bVO);
		request.setAttribute("datas", datas);
		request.setAttribute("cnt", bVO.getCnt());
	//	System.out.println("datas.size() : " + datas.size());
	//	System.out.println("cnt : " + request.getAttribute("cnt"));
		
		// 모든 게시글 보면 더보기 버튼 비활성화
		CboardVO nextBvo = bVO;
		nextBvo.setCnt(bVO.getCnt() + 2);
		// 다음에 보여줄 게시글
		ArrayList<CboardSet> nextDatas = bDAO.selectAll(nextBvo);
		// cnt와 '다음에 보여줄 게시글 개수' 차이가 2보다 크거나 같으면
		// 더보기 버튼 비활성화
		request.setAttribute("noMoreContent", nextBvo.getCnt() - nextDatas.size() >= 2 ? true : false);
	//	System.out.println("noMoreContent : " + request.getAttribute("noMoreContent"));
		
		// 내가 쓴 글 --> 세션 mid [C]에 전달
		// 전달된 mid 값이 없다면, 전체 글 더보기
		String paramMid = request.getParameter("mid");
		if(paramMid != null) { // 내가 쓴 글만 보기 --> 내가 쓴 글만 더보기
	request.setAttribute("moreContent", paramMid);
		//	System.out.println("moreContent : " + request.getAttribute("moreContent"));
		}
		
		pageContext.forward("main.jsp");
	}
	else if(action.equals("insertB")){
		if(bDAO.insert(bVO)){
	response.sendRedirect("ctrlB.jsp?action=main");
		}
		else{
	throw new Exception("insertB 오류");
		}
	}
	else if(action.equals("insertR")){
		if(bDAO.insertR(rVO)){
	pageContext.forward("ctrlB.jsp?action=main");
		}
		else{
	throw new Exception("insertR 오류");
		}
	}
	else if(action.equals("deleteB")){
		if(bDAO.delete(bVO)){
	response.sendRedirect("ctrlB.jsp?action=main");
		}
		else{
	throw new Exception("deleteB 오류");
		}
	}
	else if(action.equals("deleteR")){
		if(bDAO.deleteR(rVO)){
	pageContext.forward("ctrlB.jsp?action=main");
		}
		else{
	throw new Exception("deleteR 오류");
		}
	}
	else if(action.equals("fav")){
		if(bDAO.update(bVO)){
	pageContext.forward("ctrlB.jsp?action=main");
		}
		else{
	throw new Exception("fav 오류");
		}
	}
%>
 --%>