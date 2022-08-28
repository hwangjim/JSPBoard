package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.ReplyVO;

public class InsertReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		// 댓글 입력에 관한 Action으로 BoardDAO, replyVO 활용 !
		BoardDAO bDAO = new BoardDAO();
		ReplyVO rVO = new ReplyVO();
		
		// 댓글이 달릴 게시글 넘버인 bid, 댓글 작성자인 mid, 댓글 내용인 rmsg set해줌
		rVO.setMid(request.getParameter("mid"));
		rVO.setBid(Integer.parseInt(request.getParameter("bid")));
		rVO.setRmsg(request.getParameter("rmsg"));
		
		// if(request.getAttribute("afterReply") != null : 댓글 입력 --> 전체 내용 출력 + 전체 더보기
		// if(request.getAttribute("afterReply") == null : 댓글 입력 X 
	
	
		
		// boardDAO내 insertR 함수 활용
		// 만약 댓글을 작성했다면 
		if(bDAO.insertR(rVO)) {
			
			forward = new ActionForward();
			forward.setPath("main.do");
			// 댓글 정보 또한 가져갈거기 때문에 false로 설정 
			forward.setRedirect(false);
		}
		else {
			throw new Exception("insertR 오류");
		}
		return forward;
	}

}
