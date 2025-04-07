package gym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import db.ConnectionProvider;
import gym.vo.AdminVO;
import gym.vo.MemberVO;

public class AdminDAO {

	// 관리자 등록(sql)
	public static int registerAdminInfo(AdminVO a) {
		String sql = "insert into admin (a_id, a_pwd, a_name, hiredate, salary, a_phone) "
				+ "values (?,?,?,sysdate,?,?)";
		int re = -1;
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setInt(1, a.getaId());
			pstmt.setString(2, a.getaPwd());
			pstmt.setString(3, a.getaName());
			pstmt.setInt(4, a.getSalary());
			pstmt.setString(5, a.getaPhone());

			re = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("예외발생:" + e.getMessage());
		}
		return re;
	}
	
	public boolean isDuplicateId(int aId) {
	    String sql = "SELECT COUNT(*) FROM admin WHERE a_id = ?";
	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, aId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}


	// 관리자 정보 조회 => list 리턴
	public ArrayList<AdminVO> adminList() {
		ArrayList<AdminVO> list = new ArrayList<AdminVO>();
		String sql = "select * from admin "
				+ "order by a_Id ";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(new AdminVO(rs.getInt("a_Id"),
						rs.getString("a_Pwd"),
						rs.getString("a_Name"),
						rs.getDate("hiredate"),
						rs.getInt("salary"),
						rs.getString("a_Phone")));
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외발생:" + e.getMessage());
		}
		return list;
	}

	// 관리자 정보 수정(sql)
	public static int upDateAdminById(AdminVO a) {
		int re = -1;
		String sql = "update admin set a_pwd=?, a_phone=?, salary=? where a_id=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a.getaPwd()); // 비번
			pstmt.setString(2, a.getaPhone()); // 연락처
			pstmt.setInt(3, a.getSalary()); // 급여
			pstmt.setInt(4, a.getaId()); // 아이디

			re = pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외발생:" + e.getMessage());
		}
		return re;
	}


	// 관리자 정보 삭제(sql)
	public static int deleteAdminInfo(int aId) {
		int re = -1;
		String sql = "delete from admin where a_id = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, aId); // 관리자 아이디
			re = pstmt.executeUpdate(); // 삭제

			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외발생:" + e.getMessage());
		}
		return re;
	}

	// 회원 목록 조회(회원정보 + 멤버쉽 정보 + pt정보)
	public ArrayList<String> getMemberDetails() {
	    ArrayList<String> list = new ArrayList<>();
	    String sql = "select m.m_id, m.m_name, m.m_phone, m.m_addr, m.m_jumin, m.join_date, m.status, " +
	                 "NVL(ms.ms_type, '-') AS ms_type, NVL(TO_CHAR(ms.ms_price), '-') AS ms_price, " +
	                 "NVL(p.pt_type, '-') AS pt_type, NVL(TO_CHAR(p.pt_price), '-') AS pt_price, " +
	                 "NVL(TO_CHAR(p.t_cnt), '-') AS total_count, NVL(TO_CHAR(p.u_cnt), '-') AS used_count " +
	                 "from member m " +
	                 "left join membership ms on m.m_id = ms.m_id " +
	                 "left join pt p on m.m_id = p.m_id";

	    try {
	        Connection conn = ConnectionProvider.getConnection();
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);

	        while (rs.next()) {
	            String result = String.format(
	                "ID: %d | 이름: %s | 연락처: %s | 주소: %s | 주민번호: %s | 가입일: %s | 상태: %s\n" +
	                "  └ 멤버십 타입: %s | 가격: %s\n" +
	                "  └ PT 타입: %s | 가격: %s | 총횟수: %s | 사용횟수: %s\n",
	                rs.getInt("m_id"), rs.getString("m_name"), rs.getString("m_phone"),
	                rs.getString("m_addr"), rs.getString("m_jumin"), rs.getDate("join_date").toString(),
	                rs.getString("status"), rs.getString("ms_type"), rs.getString("ms_price"),
	                rs.getString("pt_type"), rs.getString("pt_price"),
	                rs.getString("total_count"), rs.getString("used_count")
	            );
	            list.add(result);
	        }
	        ConnectionProvider.close(conn, stmt, rs);
	    } catch (Exception e) {
	        System.out.println("예외발생: " + e.getMessage());
	    }

	    return list;
	}

	// mId를 기준으로 회원 이름 반환
	public String getMemberNameById(int mId) {
	    String memberName = null;
	    String sql = "SELECT name FROM member WHERE m_id = ?";

	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, mId);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            memberName = rs.getString("name");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return memberName;
	}

}
