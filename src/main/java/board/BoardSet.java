package board;

import java.util.ArrayList;

public class BoardSet {
	// 글 1개 + 댓글 N개
	// JAVA에서 사용할 VO를 자체 제작
	
	// boardVO를 output으로 하는 객체 boardVO 생성 
	private BoardVO boardVO;	
	// ReplyVO ArrayList를 output으로 하는 rList생성
	private ArrayList<ReplyVO> rList = new ArrayList<>();
	public BoardVO getBoardVO() {
		return boardVO;
	}
	public void setBoardVO(BoardVO boardVO) {
		this.boardVO = boardVO;
	}
	public ArrayList<ReplyVO> getrList() {
		return rList;
	}
	public void setrList(ArrayList<ReplyVO> rList) {
		this.rList = rList;
	}
	@Override
	public String toString() {
		return "BoardSet [boardVO=" + boardVO + ", rList=" + rList + "]";
	}
	
	
}
