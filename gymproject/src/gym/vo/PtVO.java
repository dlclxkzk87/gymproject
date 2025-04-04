package gym.vo;

public class PtVO {
	private int ptId;
	private int tCnt;
	private int uCnt;
	private String ptType;
	private int ptPrice;
	private int aId;
	private int mId;
	
	public PtVO(int ptId, int tCnt, int uCnt, String ptType, int ptPrice, int aId, int mId) {
		super();
		this.ptId = ptId;
		this.tCnt = tCnt;
		this.uCnt = uCnt;
		this.ptType = ptType;
		this.ptPrice = ptPrice;
		this.aId = aId;
		this.mId = mId;
	}

	public PtVO() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getPtType() {
		return ptType;
	}

	public void setPtType(String ptType) {
		this.ptType = ptType;
	}

	public int getPtPrice() {
		return ptPrice;
	}

	public void setPtPrice(int ptPrice) {
		this.ptPrice = ptPrice;
	}

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}


	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	@Override
	public String toString() {
	    return "[PT ID: " + ptId +
	           ", 총 횟수: " + tCnt +
	           ", 사용 횟수: " + uCnt +
	           ", 유형: " + ptType +
	           ", 가격: " + ptPrice +
	           ", 트레이너 ID: " + aId +
	           ", 회원 ID: " + mId + "]";
	}
	
}