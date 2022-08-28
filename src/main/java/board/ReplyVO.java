package board;

public class ReplyVO {
	private int rid;		// 댓글 index 넘버
	private String mid;		// 댓글 작성자 아이디
	private int bid;		// 게시글 index 넘버 - 어디 게시글에 댓글을 작성했는지 알기 위해
	private String rmsg;	// 댓글 내용 
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getRmsg() {
		return rmsg;
	}
	public void setRmsg(String rmsg) {
		this.rmsg = rmsg;
	}
	@Override
	public String toString() {
		return "ReplyVO [rid=" + rid + ", mid=" + mid + ", bid=" + bid + ", rmsg=" + rmsg + "]";
	}

}
