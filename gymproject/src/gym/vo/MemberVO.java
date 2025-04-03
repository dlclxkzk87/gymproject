package gym.vo;

import java.time.LocalDate;

public class MemberVO {

  private String mId;         // 회원 ID
  private String mName;       // 회원 이름
  private String mPwd;        // 비밀번호
  private String mAddr;       // 주소
  private String mJumin;      // 주민등록번호
  private String mPhone;      // 연락처
  private LocalDate joinDate; // 가입일
  private String status;      // 회원 상태 (이용중, 만료, 정지)

  // 기본 생성자
  public MemberVO() {}

  // 전체 필드 초기화 생성자
  public MemberVO(String mId, String mName, String mPwd, String mAddr,
                  String mJumin, String mPhone, LocalDate joinDate,
                  String status) {
    this.mId = mId;
    this.mName = mName;
    this.mPwd = mPwd;
    this.mAddr = mAddr;
    this.mJumin = mJumin;
    this.mPhone = mPhone;
    this.joinDate = joinDate;
    this.status = status;
  }

  // Getter / Setter
  public String getMId() {
    return mId;
  }

  public void setMId(String mId) {
    this.mId = mId;
  }

  public String getMName() {
    return mName;
  }

  public void setMName(String mName) {
    this.mName = mName;
  }

  public String getMPwd() {
    return mPwd;
  }

  public void setMPwd(String mPwd) {
    this.mPwd = mPwd;
  }

  public String getMAddr() {
    return mAddr;
  }

  public void setMAddr(String mAddr) {
    this.mAddr = mAddr;
  }

  public String getMJumin() {
    return mJumin;
  }

  public void setMJumin(String mJumin) {
    this.mJumin = mJumin;
  }

  public String getMPhone() {
    return mPhone;
  }

  public void setMPhone(String mPhone) {
    this.mPhone = mPhone;
  }

  public LocalDate getJoinDate() {
    return joinDate;
  }

  public void setJoinDate(LocalDate joinDate) {
    this.joinDate = joinDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "MemberVO{" +
        "mId='" + mId + '\'' +
        ", mName='" + mName + '\'' +
        ", mPwd='" + mPwd + '\'' +
        ", mAddr='" + mAddr + '\'' +
        ", mJumin='" + mJumin + '\'' +
        ", mPhone='" + mPhone + '\'' +
        ", joinDate=" + joinDate +
        ", status='" + status + '\'' +
        '}';
  }
}
