package gym.vo;

public class PtVO {
	private int ptId;
	private int tCnt;
	private int uCnt;
	private int ptPrice;
	private String ptType;
	private int pNo;
	private int mId;
	
	public PtVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PtVO(int ptId, int tCnt, int uCnt, int ptPrice, String ptType, int pNo, int mId) {
		super();
		this.ptId = ptId;
		this.tCnt = tCnt;
		this.uCnt = uCnt;
		this.ptPrice = ptPrice;
		this.ptType = ptType;
		this.pNo = pNo;
		this.mId = mId;
	}

	public int getPtId() {
		return ptId;
	}

	public void setPtId(int ptId) {
		this.ptId = ptId;
	}

	public int gettCnt() {
		return tCnt;
	}

	public void settCnt(int tCnt) {
		this.tCnt = tCnt;
	}

	public int getuCnt() {
		return uCnt;
	}

	public void setuCnt(int uCnt) {
		this.uCnt = uCnt;
	}

	public int getPtPrice() {
		return ptPrice;
	}

	public void setPtPrice(int ptPrice) {
		this.ptPrice = ptPrice;
	}

	public String getPtType() {
		return ptType;
	}

	public void setPtType(String ptType) {
		this.ptType = ptType;
	}

	public int getpNo() {
		return pNo;
	}

	public void setpNo(int pNo) {
		this.pNo = pNo;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}
	
	
	
}