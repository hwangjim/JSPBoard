package member;

public class MemberVO {
	private String mid;		// 회원 아이디 
	private String mpw;		// 회원 비밀번호	
	private String mname;	// 회원 이름
	private int search; // 검색시에만 사용 db 저장 xxx
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMpw() {
		return mpw;
	}
	public void setMpw(String mpw) {
		this.mpw = mpw;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public int getSearch() {
		return search;
	}
	public void setSearch(int search) {
		this.search = search;
	}
	@Override
	public String toString() {
		return "MemberVO [mid=" + mid + ", mpw=" + mpw + ", mname=" + mname + ", search=" + search + "]";
	}
	
	
}
