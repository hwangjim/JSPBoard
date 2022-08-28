package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.MemberDAO;
import member.MemberVO;

public class InsertMemberAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		// 회원 가입 관련 Action이므로 MemberDAO, VO가 필요
		MemberVO mVO = new MemberVO();
		MemberDAO mDAO = new MemberDAO();
		// 회원 가입시 기입해야할 아이디 mid, 비밀번호 mpw, 이름 mname을 set
		mVO.setMid(request.getParameter("mid"));
		mVO.setMpw(request.getParameter("mpw"));
		mVO.setMname(request.getParameter("mname"));
		
		// 만약 DAO의 insert 함수를 활용한다면
		if(mDAO.insert(mVO)) {
			forward = new ActionForward();
			forward.setPath("main.do");
			// 위 정보들은 DB에 저장되고, 브라우저 자체에 저장되지 않아 true
			forward.setRedirect(true);
		}
		else {
			throw new Exception("insertM 오류");
		}
		
		return forward;
	}
	
}
