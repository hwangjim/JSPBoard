package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardVO;

public class DeleteBoardAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ActionForward 선언 아래에서 어차피 생성자 만들어줄거기 때문에 미리..
		ActionForward forward = null;
		// 게시글 삭제 관련 Action이기 때문에 BoardDAO, VO가 각자 필요
		BoardDAO bDAO = new BoardDAO();
		BoardVO bVO = new BoardVO();
		// 게시글 삭제시 해당 게시글 index 넘버에 해당하는 것을 삭제하기 때문에 
		// bid를 set 이는 parameter로 bid를 받아오는데 getparameter는 문자열로 받아옴
		// 따라서 이를 정수형으로 parseInt해줘야함
		bVO.setBid(Integer.parseInt(request.getParameter("bid")));
		// 만약 BoardDAO에 있는 delete 함수를 사용한다면 ( 인자는 bVO )
		if(bDAO.delete(bVO)) {
			// ActionForward 를 실행하고
			forward = new ActionForward();
			// 정상 실행시 main.do로 가게끔
			forward.setPath("main.do");
			// 위 정보를 유지해 가기 때문에 redirect가 false 파라미터 값을 받는다.
			forward.setRedirect(false);
		} else {
			// 만약 정상적으로 게시글 삭제가 되지 않았다면 
			// 예외 발생시 아래와 같은 문구 출력
			throw new Exception("deleteB 오류");
		}
		// 어떤 것이든 실행이후 forward 를 반환한다.
		return forward;
	}

}
