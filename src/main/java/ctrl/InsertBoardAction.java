package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardVO;

public class InsertBoardAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ActionForward 선언 아래에서 어차피 생성자 만들어줄거기 때문에 미리..
		ActionForward forward = null;
		// 게시글 작성에 관한 Action이므로 BoardVO, DAO를 활용
		BoardVO bVO = new BoardVO();
		BoardDAO bDAO = new BoardDAO();
		// 둘다 String이라 형변환은 필요없고
		// bVO에 set해준다
		bVO.setMid(request.getParameter("mid"));
		bVO.setMsg(request.getParameter("msg"));
		
//		request.setAttribute("afterInsertBoard", true);
		// 이후 bDAO에 있는 insert에 bVO 인자값으로 넣어줌
		if(bDAO.insert(bVO)) {
			
			forward = new ActionForward();
			forward.setPath("main.do");	
			// 게시글 정보를 저장해서 들고가기위해 
			// false
			forward.setRedirect(false);
		}
		else {
			throw new Exception("insertB 오류");
		}
		return forward;
	}
}
