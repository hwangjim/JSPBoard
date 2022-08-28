package ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.BoardDAO;
import board.BoardVO;
import board.ReplyVO;
import member.MemberDAO;
import member.MemberVO;

public class DeleteMemberAction implements Action {

   @Override
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	// ActionForward 선언 아래에서 어차피 생성자 만들어줄거기 때문에 미리..
	  ActionForward forward = null;
	  // 회원탈퇴시 사용하는 액션이기 때문에 MemberDAO, VO는 당연히 있어야하고
	  // 탈퇴시 작성된 게시글, 댓글이 있어서는 안되기 때문에 BoardDAO, VO, ReplyVO가 있어야함
      MemberDAO mdao = new MemberDAO();
      BoardDAO dao = new BoardDAO();
      MemberVO mvo = new MemberVO();
      BoardVO bvo = new BoardVO();
      ReplyVO rvo = new ReplyVO();
      
      // 탈퇴시 mvo, bvo, rvo를 판별하기 때문에 mid를 getparameter로 set
      mvo.setMid(request.getParameter("mid"));
      bvo.setMid(request.getParameter("mid"));
      rvo.setMid(request.getParameter("mid"));
      
      // 위와 같은이유로 역시 mpw도 set해준다 
      // 비밀번호는 membervo에만 저장되어있기 때문에 위와는 달리 mvo에만 set
      mvo.setMpw(request.getParameter("mpw"));
      // 만약 게시글작성 == null 인 동시에 댓글 작성 == null이라면
      if(dao.selectOneB(bvo)== null && dao.selectOneR(rvo)==null ) {
    	  // 그와 동시에 mvo에서 mdao에 있는 delete 함수를 이용해 탈퇴한다면
         if(mdao.delete(mvo)) {
        	 // session을 가져오고
            HttpSession session = request.getSession();
            // session내 저장되어있는 회원 정보를 삭제한다.
            session.invalidate();
            forward = new ActionForward();
            // main.do로 가고
            forward.setPath("main.do");
            // 이 정보는 저장하지 않고 간다 즉, 동일 아이디 비밀번호로 회원가입이 가능
            forward.setRedirect(true);
         }
      } else {
    	  System.out.println("로그 : 탈퇴실패");
         throw new Exception("탈퇴 실패");
      }
      // 위 함수 전부 마친 후 forward 반환
      return forward;
   }
}