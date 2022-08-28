package member;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import util.JDBCUtil;



public class MemberDAO {
	Connection conn;
	PreparedStatement pstmt;
	// selectOne == 로그인시 사용하는 sql문으로 사용자 아이디인 mid, 비밀번호인 mpw를 받아 사용
	// LoginAction에 사용된다.
	final String sql_selectOne="SELECT * FROM MEMBER WHERE MID=? AND MPW=?";
	// insert == 회원가입시 사용하는 sql문으로 InsertMemberAction, signup.jsp에 사용된다.
	final String sql_insert="INSERT INTO MEMBER VALUES(?,?,?)";
	// delete == 회원 탈퇴시 사용하는 sql문으로 selectOne과 동일하게 mid, mpw를 받아 DeleteMemberAction에 사용된다.
	final String sql_delete="DELETE FROM MEMBER WHERE MID=? AND MPW=?";
	// selectAll == 최근 회원가입한 3명을 보여주는 sql문으로 
	// MainAction에 사용된다.
	final String sql_selectAll="SELECT * FROM (SELECT A.*,ROWNUM AS RNUM FROM (SELECT * FROM MEMBER ORDER BY ROWNUM DESC) A WHERE ROWNUM<=3) WHERE RNUM>=1";
	
	
	// 최근 회원가입한 3명을 보여주는 selectAll 메소드로 배열에 저장한다.
	public ArrayList<MemberVO> selectAll(MemberVO mvo){
		// 3명을 보여줘야하기 때문에 배열리스트에 저장하기위한 datas 객체 생성
		ArrayList<MemberVO> datas=new ArrayList<MemberVO>();
		// JDBCUtil 연결
		conn=JDBCUtil.connect();
		try {
			// selectAll sql문 활용
			pstmt=conn.prepareStatement(sql_selectAll);
			ResultSet rs=pstmt.executeQuery();
			// rs가 계속될동안 
			while(rs.next()) {
				// memberVO에서 받아올것이기 때문에 객체 생성
				MemberVO memberVO=new MemberVO();
				// 최근 회원가입한 3명의 mid, mname을 불러온다
				memberVO.setMid(rs.getString("MID"));
				memberVO.setMname(rs.getString("MNAME"));
				// 이를 datas에 add한다.
				datas.add(memberVO);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		System.out.println("최근 3명 메인에 불러오기 완료");
		return datas;
	}
	// 로그인시 사용하는 selectOne 함수 
	public MemberVO selectOne(MemberVO vo) {
		// JDBCUtil 연결
		conn=JDBCUtil.connect();
		try {
			// selectOne sql문 사용
			pstmt=conn.prepareStatement(sql_selectOne);
			// 사용자의 아이디인 mid, 비밀번호인 mpw를 받아온다
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getMpw());
			ResultSet rs=pstmt.executeQuery();
			// 만약 rs가 온다면
			if(rs.next()) {
				// memberVO를 받아오는 data 객체 선언
				MemberVO data=new MemberVO();
				// 사용자의 mid, mname, mpw를 set
				data.setMid(rs.getString("MID"));
				data.setMname(rs.getString("MNAME"));
				data.setMpw(rs.getString("MPW"));
				// 이 모든것을 return
				// rs.next()라면 data를 return
				return data;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		// 로그인 성공시 이 모든것들이 저장되어 로그인 상태 유지
		System.out.println("로그인 성공 ! 유지됨");
		return null;
	}
	// 회원가입시 사용하는 insert 함수
	public boolean insert(MemberVO vo) {
		// JDBCUtil 연결
		conn=JDBCUtil.connect();
		try {
			// insert sql문 사용
			pstmt=conn.prepareStatement(sql_insert);
			// 회원가입시에 필요한 정보들을 기입하는데 이때 이 정보들은
			// mid, mpw, mname이다.
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getMpw());
			pstmt.setString(3, vo.getMname());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		// boolean 타입으로 회원가입 성공시 true
		System.out.println("로그 : 회원가입 성공 !");
		return true;
	}
	// 회원탈퇴시 사용하는 delete 함수
	public boolean delete(MemberVO vo) {
		// JDBCUtil 연결
		conn=JDBCUtil.connect();
		try {
			// delete sql문 사용
			pstmt=conn.prepareStatement(sql_delete);
			// 사용자의 아이디인 mid, mpw를 활용 이때 실질적 활용은 mpw
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getMpw());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		// 회원탈퇴 성공시 true 반환
		System.out.println("로그 : 회원 탈퇴 성공 ! ");
		return true;
	}
}
