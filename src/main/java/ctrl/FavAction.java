package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardDAO;
import board.BoardVO;

public class FavAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ActionForward 선언 아래에서 어차피 생성자 만들어줄거기 때문에 미리..
		ActionForward forward = null;
		// 게시글 내 좋아요 횟수를 증가시키는 작업이기때문에 BoardDAO, VO를 활용
		BoardDAO bDAO = new BoardDAO();
		BoardVO bVO = new BoardVO();
		
		//  마찬가지로 getparameter기 때문에 형변환 후 vo에 set해준다.
		bVO.setBid(Integer.parseInt(request.getParameter("bid")));
		
		// 만약 DAO에 있는 update 함수가 실행된다면 
		// 즉 좋아요 개수가 1개 증가한다면
		if(bDAO.update(bVO)) {
			forward = new ActionForward();
			// main.do로 가게끔
			forward.setPath("main.do");
			// 갯수가 증가된 정보를 가지고 갈거기 때문에 forward 즉 redirect값이 false
			forward.setRedirect(false);
		}
		else {
			throw new Exception("fav 오류");
		}
		return forward;
	}

}
