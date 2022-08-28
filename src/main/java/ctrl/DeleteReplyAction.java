package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.ReplyVO;

public class DeleteReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ActionForward 선언 아래에서 어차피 생성자 만들어줄거기 때문에 미리..
		ActionForward forward = null;
		// 댓글 삭제 기능이기때문에 BoardDAO, ReplyVO 객체 , 생성자 필요
		// BoardVO가 필요없는이유는 단순 댓글 작성에 관련된것이기 때문이고
		// BoardVO는 게시글에 작성된 정보라 댓글과 별개
		// ReplyVO 내에는 외래키로 받아오는 값이 존재하기 때문
		BoardDAO bDAO = new BoardDAO();
		ReplyVO rVO = new ReplyVO();
		
		// 댓글 index 넘버인 rid는 정수형이기 때문에 bid와 같이 정수형으로 형변환 필요
		rVO.setRid(Integer.parseInt(request.getParameter("rid")));
		
		// 만약 bDAO에 있는 deleteR을 사용하고 rvo를 인자로 받아 
		// 댓글을 삭제하게된다면
		if(bDAO.deleteR(rVO)) {
			
			forward = new ActionForward();
			// main.do로 이동
			forward.setPath("main.do");
			// 위 삭제한 정보를 가져갈 것이기 떄문에 redirect false 사용
			forward.setRedirect(false);
		} else {
			throw new Exception("deleteR 오류");
		}
		// 어떤 것이든 실행 이후 forward반환
		return forward;
	}
	
}
