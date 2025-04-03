package gym.vo;

import java.sql.Date;

public class AdminVO {
	private int aId;
	private String aPwd;
	private String aName;
	private Date hiredate;
	private int salary;
	private String aPhone;

	public AdminVO() {
		super();
	}

	public AdminVO(int aId, String aPwd, String aName, Date hiredate, int salary, String aPhone) {
		super();
		this.aId = aId;
		this.aPwd = aPwd;
		this.aName = aName;
		this.hiredate = hiredate;
		this.salary = salary;
		this.aPhone = aPhone;
	}



	public AdminVO(int aId2, String aPwd, String aName, int salary, String aPhone) {
		// TODO Auto-generated constructor stub
	}

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

	public String getaPwd() {
		return aPwd;
	}

	public void setaPwd(String aPwd) {
		this.aPwd = aPwd;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getaPhone() {
		return aPhone;
	}

	public void setaPhone(String aPhone) {
		this.aPhone = aPhone;
	}

	@Override
	public String toString() {
		return "AdminVO [aId=" + aId + ", aPwd=" + aPwd + ", aName=" + aName + ", hiredate=" + hiredate + ", salary="
				+ salary + ", aPhone=" + aPhone + "]";
	}

}
