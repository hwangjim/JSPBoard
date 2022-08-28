package ctrl;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.BoardDAO;
import board.BoardSet;
import board.BoardVO;
import member.MemberDAO;
import member.MemberVO;

public class MainAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// filter가 인코딩 담당
//		 request.setCharacterEncoding("UTF-8");
//		 response.setCharacterEncoding("UTF-8");
		// 드디어 메인.. mainAction에선 모든 것들을 활용하기 때문에 모든 vo, dao들을 사용한다
		BoardDAO bdao = new BoardDAO();
		BoardVO bvo = new BoardVO();
		MemberVO mvo = new MemberVO();
		MemberDAO mdao = new MemberDAO();
		// 더보기를 위해 사용되는 cnt 2개씩 보게끔 만들어준다. 
		String paramCnt = request.getParameter("cnt");
		// 만약 Parameter로 get한 cnt가 null이거나 cnt가 아무것도 없는상태라면(null은 아님)
		// 즉 더보기를 누르지않았다면
		if(paramCnt == null || paramCnt.equals("")){
			// 기본 cnt를 2로 설정 즉 더보기를 누르기전에는 2개씩 보여준다.
			bvo.setCnt(2); // 향후 초기화 매개변수 등으로 설정가능
		}
		else {
			// cnt를 정수형으로 형변환
			// 이후 main.jsp에선 누를때마다 2개씩 증가
			bvo.setCnt(Integer.parseInt(paramCnt));

		}
		// mdao에 있는 selectAll 함수 사용이후에 나오는 값들을 member를 배열리스트에 담는다
		ArrayList<MemberVO> member = mdao.selectAll(mvo);
		// 속성을 member로 부여하고 member값을 member속성에 부여
		// 최근 가입한 3명을 받아온다
		request.setAttribute("member", member);
		// 최근 회원가입한 3명의 글을 보여주는 showContent 이때 showContent 값은 최근 가입한 인원의 이름을 뜻한다
		String showContent = request.getParameter("showContent");
		System.out.println("showContent : [" + showContent + "]");
//		String paramMid = request.getParameter("mid");
//		String paramRmsg = request.getParameter("rmsg");
		//		System.out.println("request.getParameter(mid) : " + paramMid);
//		System.out.println("request.getParameter(rmsg) : " + paramRmsg);
		
		// 출력할 게시글의 작성자를 세션에 저장
		HttpSession session = request.getSession(); 	
		// 메인으로 가기 / 내가 쓴 글 보기 / 최근 가입 회원이 쓴 글에 접속
		// 만약 최근 회원가입한 인원 & 내가 쓴글이 null값이 아니라면
		if(showContent != null) {		
			// 메인으로가기
			if(showContent.equals("main")) { 
				// 세션 삭제 >> 전체 글 보기
				session.removeAttribute("moreContent"); 

				// 내가 쓴 글 / 최근 회원이 쓴 글
			} else { 		
				// 작성자 세션 저장
				// moreContent라는 속성에 showContent 값저장
				session.setAttribute("moreContent", showContent); 
				// 해당 작성자의 모든 게시글 출력하기 위한 mid set
				bvo.setMid(showContent); 	
			}
		} else {	// 작성, 삭제, 좋아요 등 게시글 조작 -> showContent를 전달하지 않음
					// 그래서 세션에 있는 작성자 정보 이용
					// ex. 내가 쓴 글 보기(나의 mid 세션에 저장) >> 좋아요, 댓글 등 기능 수행 (showContent 전달x)
			bvo.setMid((String)session.getAttribute("moreContent"));	// 바로 직전 세션에 저장된 작성자의 글 출력(전체 게시글 포함)
		}
		
		// ArrayList boardset을 output으로 하는 datas = bdao내 selectAll 함수 활용
		ArrayList<BoardSet> datas = bdao.selectAll(bvo);
		System.out.println(datas);
		// 속성 부여
		request.setAttribute("datas", datas);
		// 더보기 관련 cnt는 bvo내 get한 cnt로 값을 설정
		request.setAttribute("cnt", bvo.getCnt());
		
		// 모든 게시글 보면 더보기 버튼 비활성화
		
		BoardVO nextBvo = bvo;
		// 다음에 보여줄 게시글 (2개씩)
		nextBvo.setCnt(bvo.getCnt() + 2);
		// 즉 더보기 버튼을 누르면 boardset에 있는 게시글이 2개씩 나옴
		ArrayList<BoardSet> nextDatas = bdao.selectAll(nextBvo);
		// cnt와 '다음에 보여줄 게시글 개수' 차이가 2보다 크거나 같으면 더보기 버튼 비활성화
		request.setAttribute("noMoreContent", nextBvo.getCnt() - nextDatas.size() >= 2 ? true : false);
		System.out.println("noMoreContent : " + request.getAttribute("noMoreContent"));

		// 내가 쓴 글 --> 세션 mid를 [C]에 전달 
		// 전달된 mid 값이 없다면, 전체 글 더보기
		// if(" 내가 쓴 글"  에 있는 mid " != null && 
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("/main.jsp");
		// 이 게시판 정보는 forward로 정보를 저장해 무조건 가져간다
		forward.setRedirect(false);
		return forward;
	}

}

