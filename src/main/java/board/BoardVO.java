package board;

public class BoardVO {
	private int bid;		// 게시글 index 넘버
	private String mid;		// 작성자 아이디
	private String msg;		// 게시글 내용
	private int favcnt;		// 좋아요 개수
	private int rcnt;		// 댓글 개수
	private int cnt; // 메인 페이지에 보여줄 글의 개수 [JAVA]
	
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getFavcnt() {
		return favcnt;
	}
	public void setFavcnt(int favcnt) {
		this.favcnt = favcnt;
	}
	public int getRcnt() {
		return rcnt;
	}
	public void setRcnt(int rcnt) {
		this.rcnt = rcnt;
	}
	
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	@Override
	public String toString() {
		return "BoardVO [bid=" + bid + ", mid=" + mid + ", msg=" + msg + ", favcnt=" + favcnt + ", rcnt=" + rcnt
				+ ", cnt=" + cnt + "]";
	}
	
}
