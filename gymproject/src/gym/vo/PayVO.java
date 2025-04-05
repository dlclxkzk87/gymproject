package gym.vo;

import java.time.LocalDateTime;

public class PayVO {
   private int pNo;
   private String pType;
   private LocalDateTime pDate;
   private int pPrice;
   private int mId;
   
   public PayVO(int pNo, String pType, int pPrice, int mId) {
	super();
	this.pNo = pNo;
	this.pType = pType;
	this.pPrice = pPrice;
	this.mId = mId;
}
public PayVO() {
      super();
      // TODO Auto-generated constructor stub
   }
   public int getpNo() {
      return pNo;
   }
   public void setpNo(int pNo) {
      this.pNo = pNo;
   }
   public String getpType() {
      return pType;
   }
   public void setpType(String pType) {
      this.pType = pType;
   }
 
   public LocalDateTime getpDate() {
	return pDate;
}
public void setpDate(LocalDateTime pDate) {
	this.pDate = pDate;
}
public int getpPrice() {
      return pPrice;
   }
   public void setpPrice(int pPrice) {
      this.pPrice = pPrice;
   }
   public int getmId() {
      return mId;
   }
   public void setmId(int mId) {
      this.mId = mId;
   }
 
   
}
