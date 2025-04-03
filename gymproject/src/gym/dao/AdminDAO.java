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

	// 관리자 정보 조회 => list 리턴
	public ArrayList<AdminVO> adminList() {
		ArrayList<AdminVO> list = new ArrayList<AdminVO>();
		String sql = "select * from admin";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(new AdminVO(rs.getInt("aId"), rs.getString("aPwd"), rs.getString("aName"),
						rs.getDate("hiredate"), rs.getInt("salary"), rs.getString("aPhone")));
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
		String sql = "update admin set apwd=?, aphone=?, salary=? where aid=?";
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

	// 관리자 정보 수정(java)
	public static void upDateAdmin() {
		Scanner sc = new Scanner(System.in);
        System.out.print("수정할 관리자 아이디를 입력하세요: ");
        int aId = sc.nextInt();
        sc.nextLine(); // 버퍼 비우기

        System.out.print("새 비밀번호를 입력하세요: ");
        String Pwd = sc.nextLine();

        System.out.print("새 연락처를 입력하세요: ");
        String aPhone = sc.nextLine();

        System.out.print("새 급여를 입력하세요: ");
        int salary = sc.nextInt();

        AdminVO admin = new AdminVO(aId, Pwd, null, null, salary, aPhone);

        int result = upDateAdminById(admin);

        if (result > 0) {
            System.out.println("관리자 정보가 성공적으로 수정되었습니다!");
        } else {
            System.out.println("관리자 정보 수정에 실패했습니다.");
        }
        sc.close();
	}


	// 관리자 정보 삭제(sql)
	public static int deleteAdminInfo(int aId) {
		int re = -1;
		String sql = "delete from admin where a_id = ?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, aId); // 관리자 아이디
			re = pstmt.executeUpdate(sql); // 삭제

			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외발생:" + e.getMessage());
		}
		return re;
	}

	// 관리자 정보 삭제(java)
	public static void deleteAdmin() {
		Scanner sc = new Scanner(System.in);

		System.out.print("삭제할 관리자 아이디를 입력하세요: ");
        int aId = sc.nextInt();

        int result = deleteAdminInfo(aId);

        if (result > 0) {
            System.out.println("관리자 정보가 성공적으로 삭제되었습니다!");
        } else {
            System.out.println("관리자 삭제에 실패했습니다. 아이디를 다시 확인하세요.");
        }
        sc.close();
	}

//	// pt 회원정보 조회
//	public ArrayList<MemberVO> ptMemberList(){
//		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
//		String sql = "select * "
//				+ "from member m, pt p "
//				+ "where m.m_id = p.m_id "
//				+ "and status = '이용중'";
//
//		try {
//			Connection conn = ConnectionProvider.getConnection();
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(sql);
//
//			while(rs.next()) {
//				list.add(new MemberVO(
//						rs.getInt("mId"),
//						rs.getString("pWd"),
//						rs.getString("mname"),
//						rs.getString("mPhone"),
//						rs.getString("mAddr"),
//						rs.getString("mJumin"),
//						rs.getDate("joinDate"),
//						rs.getString("status")));
//			}
//			ConnectionProvider.close(conn, stmt);
//
//		} catch (Exception e) {
//			System.out.println("예외발생:"+e.getMessage());
//		}
//		return list;
//	}

}
