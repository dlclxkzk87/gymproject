package gym.vo;

import java.sql.Date;

public class MembershipVO {
	private int msId;
	private String msType;
	private int msPrice;
	private Date msStart;
	private Date msEnd;
	private int mId;
	
	public MembershipVO() {
		super();
	}

	public MembershipVO(int msId, String msType, int msPrice, Date msStart, Date msEnd, int mId) {
		super();
		this.msId = msId;
		this.msType = msType;
		this.msPrice = msPrice;
		this.msStart = msStart;
		this.msEnd = msEnd;
		this.mId = mId;
	}

	public int getMsId() {
		return msId;
	}

	public void setMsId(int msId) {
		this.msId = msId;
	}

	public String getMsType() {
		return msType;
	}

	public void setMsType(String msType) {
		this.msType = msType;
	}

	public int getMsPrice() {
		return msPrice;
	}

	public void setMsPrice(int msPrice) {
		this.msPrice = msPrice;
	}

	public Date getMsStart() {
		return msStart;
	}

	public void setMsStart(Date msStart) {
		this.msStart = msStart;
	}

	public Date getMsEnd() {
		return msEnd;
	}

	public void setMsEnd(Date msEnd) {
		this.msEnd = msEnd;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}
	
}
