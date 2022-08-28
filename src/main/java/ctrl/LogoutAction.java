package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그 아웃시 session역시 필요 클라이언트별로 만들어지는 객체이기 때문
		HttpSession session = request.getSession();
		// 로그아웃시 session에서 없앤다.
		session.invalidate();
		
		ActionForward forward = new ActionForward();
		forward.setPath("main.do");
		// 정보를 가져가지 않기 때문에 redirect true
		forward.setRedirect(true); // sendRedirect
		return forward;
	}
	
}
