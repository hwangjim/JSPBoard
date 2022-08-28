package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.MemberDAO;
import member.MemberVO;

public class LoginAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 로그인 관련 Action이기 때문에 MemberVO, DAO 활용
		MemberVO mVO = new MemberVO();
		MemberDAO mDAO = new MemberDAO();
		
		// 로그인시 회원 아이디 mid, 비밀번호 mpw를 기입해야하기 때문에
		// set 해줌
		mVO.setMid(request.getParameter("mid"));
		mVO.setMpw(request.getParameter("mpw"));
		
		
		// 만약 mDAO내 selectOne 함수를 사용했을때 null 값이 아니라면 즉
		// 로그인을 했다면 !
		if(mDAO.selectOne(mVO) != null) {
			// 클라이언트 별로 존재하게끔 하는session 활용
			HttpSession session = request.getSession();
			// session에 userId라는 속성 set, 이는 memberVO 내 get한 mid값이다
			// 즉 로그인한 아이디 바로 그것.
			session.setAttribute("userId", mVO.getMid());
		}
		else {
			System.out.println("로그 : 로그인 실패");
		}
		
		ActionForward forward = new ActionForward();
		forward.setPath("main.do");
		// 위 정보를 저장해 계속해서 로그인 화면을 출력시킬것이 아니기 때문에 true
		// 즉, 클라이언트별로 존재하는 session 에 있기 떄문에
		forward.setRedirect(true);
		return forward;
	}

}
