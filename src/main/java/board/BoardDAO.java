package board;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import util.JDBCUtil;

public class BoardDAO {
   Connection conn;
   PreparedStatement pstmt;
   // selectAll과 AllM과는 where mid = ? 차이 
   // selectAll == 전체 게시글 더보기 sql문
   // rownum == 게시글 등록순으로 일정 번호를 매김
   final String sql_selectAll="SELECT * FROM (SELECT * FROM  BOARD ORDER BY BID DESC) WHERE ROWNUM <=?";
   // selectOneB == 게시글 내 mid 찾기 -> 회원탈퇴시 글이 작성되면 안되기 때문에 이를 판별하는 sql문
   final String sql_selectOneB="SELECT * FROM BOARD WHERE MID=?";
   // selectOneB == 댓글 내 mid 찾기 -> 회원탈퇴시 글이 작성되면 안되기 때문에 이를 판별하는 sql문
   final String sql_selectOneR="SELECT * FROM REPLY WHERE MID=?";
   
   // selectAll_M == mid를 활용해 본인 게시글을 보여주고 rownum을 활용해 더보기가 가능케하는 sql문
   // ? = cnt (내가 쓴 글에서 더보기까지 생각한다면 where rownum 붙이기
   final String sql_selectAll_M="SELECT * FROM (SELECT * FROM BOARD WHERE MID = ? ORDER BY BID DESC) WHERE ROWNUM <=?"; 
   // selectAll_R == bid를 통해 댓글을 전부 보여주는 sql문 
   
   final String sql_selectAll_R="SELECT * FROM REPLY WHERE BID=? ORDER BY RID DESC";
   // insert == bid, mid, msg를 통해 게시판에 글을 작성하고 저장하게끔 하는 sql문
   final String sql_insert="INSERT INTO BOARD(BID,MID,MSG) VALUES((SELECT NVL(MAX(BID),0)+1 FROM BOARD), ?,?)";
   // delete == bid를 통해 게시판 글을 삭제하는 sql문
   final String sql_delete="DELETE FROM BOARD WHERE BID=?";
   // insert_R == 기존에 작성된 글에 댓글을 작성하면 추가해주는 sql문
   final String sql_insert_R="INSERT INTO REPLY(RID,MID,BID,RMSG) VALUES((SELECT NVL(MAX(RID),0)+1 FROM REPLY),?,?,?)";
   // delete_R == rid를 통해 댓글 삭제하는 sql문
   final String sql_delete_R="DELETE FROM REPLY WHERE RID=?";
   // update == bid를 통해 게시글 좋아요 개수 count하는 sql문 
   final String sql_update="UPDATE BOARD SET FAVCNT=FAVCNT+1 WHERE BID=?"; 
   
  
  
   // 게시글 중 특정 회원이 작성한 게시글을 보여주는 함수 selectOneB
   // deletememberAction에서 회원탈퇴시 회원이 작성한 게시글이 있는지를 식별
   public BoardVO selectOneB(BoardVO vo) {
	   // JDBCUtil과 연결
      conn=JDBCUtil.connect();
      try {
    	  // selectOneB sql문활용
         pstmt=conn.prepareStatement(sql_selectOneB);
         // 회원 id인 mid를 set해준다.
         // mid를 set해주는 이유는 회원 탈퇴시 이 mid로 작성한 글이 있는지를
         // 판별하기 위해서임
         pstmt.setString(1, vo.getMid());
         ResultSet rs=pstmt.executeQuery();
         // 만약 rs가 계속된다면
         if(rs.next()) {
        	 // boardVO를 활용한 data 객체 생성
            BoardVO data=new BoardVO();
            // 이곳에 mid를 set해주고
            data.setMid(rs.getString("MID"));
            // data를 return해준다.
            return data;
         }
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }      
      // 만약 함수가 실행되었다면 null값 반환
      System.out.println("로그 : 작성된 게시글이 없어 탈퇴합니다!");
      return null;
   }
   // 게시글 중 특정 회원이 작성한 댓글을 보여주는 함수 selectOneR
   // deletememberAction에서 회원탈퇴시 회원이 작성한 댓글이 있는지를 식별
   public ReplyVO selectOneR(ReplyVO vo) {
	   // JDBCUtil을 연결
      conn=JDBCUtil.connect();
      try {
    	  // selectOneR sql문 활용
         pstmt=conn.prepareStatement(sql_selectOneR);
         // mid를 set해준다.
         // 이유또한 selectOneB sql문을 활용했을때와 같다.
         pstmt.setString(1, vo.getMid());
         ResultSet rs=pstmt.executeQuery();
         // 만약 rs가 계속될때 
         if(rs.next()) {
        	 // replyVO data 객체 선언
            ReplyVO data=new ReplyVO();
            // mid를 set해주고
            data.setMid(rs.getString("MID"));
            // data를 반환
           
            return data;
         }
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }      
      // 만약 탈퇴가 정상적으로 진행된다면
      System.out.println("로그 : 작성된 댓글이 없어 탈퇴합니다!");
      return null;
   }
   // 게시판에 회원이 글을 작성하게끔 하는 insert 함수 boolean으로 output
   public boolean insert(BoardVO bvo) {
	   // JDBCUtil 연결
      conn=JDBCUtil.connect();
      try {
    	  // insert sql문 활용
         pstmt=conn.prepareStatement(sql_insert);
         // mid, msg를 set해준다 
         // 이유는 단순 게시글에 글을 회원이 작성하는 것이기 때문에
         // 회원 아이디인 mid, 게시글 내용인 msg가 필요하기 때문
         pstmt.setString(1, bvo.getMid());
         pstmt.setString(2, bvo.getMsg());
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
         // 예외 발생시 false
         System.out.println("로그 : 게시판 내 글 게시 실패!");
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      System.out.println("로그 : 게시판 내 글 게시 성공!");
      return true;
   }
   // 게시판 내 글 삭제를 하는 함수 delete
   // 이때 글 삭제는 본인만이 할 수 있다. 
   public boolean delete(BoardVO bvo) {
	   // JDBCUtil 연결
      conn=JDBCUtil.connect();
      try {
    	  // delete sql문 활용
         pstmt=conn.prepareStatement(sql_delete);
         // bid를 set해줌
         // 그 이유는 우선 mid를 사용하면 특정 id를 가진 회원의 게시글 전부가 삭제되기 때문에
         // 게시글 넘버인 bid를 특정해 해당 bid만을 삭제하기 위함
         pstmt.setInt(1,bvo.getBid());
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
         System.out.println("로그 : 게시글 삭제 실패 !");
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      System.out.println("로그 : 게시글 삭제 성공 !");
      return true;
   }
   // 게시글 내 댓글을 작성케 하는 insertR 함수 
   // 역시 output으로 boolean을 활용
   public boolean insertR(ReplyVO rvo) {
	   // JDBCUtil 연결
      conn=JDBCUtil.connect();
      try {
    	  // insert_R sql문 활용
         pstmt=conn.prepareStatement(sql_insert_R);
         // 해당 mid가 작성한 bid 넘버 글에 rmsg 즉 댓글을 작성하기 위해
         // 3가지 모두 set
         pstmt.setString(1, rvo.getMid());
         pstmt.setInt(2, rvo.getBid());
         pstmt.setString(3, rvo.getRmsg());
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
         System.out.println("로그 : 댓글 작성 실패 !");
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      System.out.println("로그 : 댓글 작성 성공 !");
      return true;
   }
   // 댓글을 작성하는 함수 deleteR
   public boolean deleteR(ReplyVO rvo) {
	   // JDBCUtil 연결 
      conn=JDBCUtil.connect();
      try {
    	  // delete_R sql문 활용 
         pstmt=conn.prepareStatement(sql_delete_R);
         // 해당 댓글을 삭제하기 위해선 판별이 가능한 댓글 넘버인 rid가 필요해 set
         pstmt.setInt(1, rvo.getRid());
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
         System.out.println("로그 : 댓글 삭제 실패 !");
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      System.out.println("로그 : 댓글 삭제 성공 !");
      return true;
   }
   // 게시글의 좋아요 개수를 증가시키는 update 함수
   public boolean update(BoardVO bvo) {
	   // JDBCUtil 연결
      conn=JDBCUtil.connect();
      try {
    	  // update sql문 활용
         pstmt=conn.prepareStatement(sql_update);
         // 해당 bid내 좋아요를 클릭하기 위해 bid를 활용
         pstmt.setInt(1, bvo.getBid());
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
         System.out.println("로그 : 좋아요 실패 !");
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      System.out.println("로그 : 좋아요 성공 !");
      return true;
   }
   // 대망의 selectAll...
   // selectAll이 들어가는 sql문을 각자 따로 작성하기엔 데이터 낭비, 
   // 재활용도 가능하기에 몰아서 사용
   public ArrayList<BoardSet> selectAll(BoardVO bvo){ //  
	   // 게시글 1개 + 댓글 n개를 전부 출력하기 위해 BoardSet을 output으로
	   // 하는 배열리스트 datas 객체 선언
      ArrayList<BoardSet> datas=new ArrayList<BoardSet>();
      // JDBCUtil 선언
      conn=JDBCUtil.connect();
      try {
    	  // 만약 전달받은 mid가 없다면
    	  if(bvo.getMid() == null) {
    		  // => 전체 게시글을 보여주기 위한 selectAll sql문 활용
         pstmt=conn.prepareStatement(sql_selectAll);
         // cnt를 활용하는 이유는 전체 게시글더보기 클릭시에 사용하기 때문에
         pstmt.setInt(1, bvo.getCnt());
      } else {
    	  // 만약 mid를 전달받았다면
    	  // => 본인 게시글을 더보기한다.
    	  // 이때 selectAll_M sql문을 활용해 본인 게시글 더보기
    	  pstmt = conn.prepareStatement(sql_selectAll_M);
    	  // 본인의 글이기 때문에 mid활용
    	  pstmt.setString(1, bvo.getMid());
    	  // 본인의 글 더보기를 위해 cnt 활용
    	  pstmt.setInt(2, bvo.getCnt());
      }
    	  
         ResultSet rs=pstmt.executeQuery();
         // rs가 계속될 동안 
         while(rs.next()) {
        	 // boardset bs 객체를 선언
            BoardSet bs=new BoardSet();
            // boardVO boardVO 객체 선언
            BoardVO boardVO=new BoardVO();
            // 하나의 게시글을 달면 
            // 게시글 넘버인 bid, 좋아요 개수인 favcnt, 
            // 작성자가 누구인지 확인하는 mid, 게시글 내용인 msg
            // 마지막으로 댓글의 개수 rcnt set
            boardVO.setBid(rs.getInt("BID"));
            boardVO.setFavcnt(rs.getInt("FAVCNT"));
            boardVO.setMid(rs.getString("MID"));
            boardVO.setMsg(rs.getString("MSG"));
            boardVO.setRcnt(rs.getInt("RCNT")); ///// rList.size();
            // bs에 boardvo에서 set한 것을 set한다
            bs.setBoardVO(boardVO);
            
            // 댓글을 모아 보여주는 rList라서 replyVO를 제네릭으로 하는 배열리스트 
            ArrayList<ReplyVO> rList=new ArrayList<ReplyVO>();
            // selectAll_R sql문활용
            // bid에 해당하는 댓글을 전부 보여줌
            pstmt=conn.prepareStatement(sql_selectAll_R); 
            // 해당 bid에 있는 댓글들 모두를 보여주기 위해 bid를 set
            pstmt.setInt(1, rs.getInt("BID")); //  BID
            ResultSet rs2=pstmt.executeQuery();
            while(rs2.next()) {
            	// 댓글을 보여주기 위해 replyVO 객체 생성
               ReplyVO replyVO=new ReplyVO();
               // 어디있는 댓글인지 판별하기위한 bid
               // 누가 작성했는지 보기위한 mid
               // 댓글 넘버를 알기위한 rid
               // 댓글 내용을 보기위한 rmsg
               replyVO.setBid(rs2.getInt("BID"));
               replyVO.setMid(rs2.getString("MID"));
               replyVO.setRid(rs2.getInt("RID"));
               replyVO.setRmsg(rs2.getString("RMSG"));
               // 이 모든것들을 set해준 replyvo를 rList에 set해줌
               rList.add(replyVO);
            }
            // 각 게시글에 작성된 게시글이 몇개인지 파악하기위해 
            // rList.size만큼 boardvo에 rcnt를 set해준다.
            
            boardVO.setRcnt(rList.size());
            // 각 게시글에 작성된 댓글 개수 n개를 보여주기위해
            // boardset bs에 rList set 해준다.
           
            bs.setrList(rList);            
            // 게시글과 댓글 목록들을 보여주기위해 boardset datas에 bs를 add
            
            datas.add(bs);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      System.out.println("로그 : 게시글 목록들 ! !");
      return datas;
   }
}