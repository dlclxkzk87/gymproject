package gym.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionProvider;
import gym.vo.MemberVO;

public class MemberDAO {

	// 회원 등록
	public int insertMember(MemberVO member) {
		String sql = "INSERT INTO member (m_id, m_pwd, m_name, m_phone, m_addr, m_jumin, join_date, status) "
				+ "VALUES (?, ?, ?, ?, ?, ?, sysdate, ?)";
		int re = -1;
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, member.getMId());
			pstmt.setString(2, member.getMPwd());
			pstmt.setString(3, member.getMName());
			pstmt.setString(4, member.getMPhone());
			pstmt.setString(5, member.getMAddr());
			pstmt.setString(6, member.getMJumin());
			pstmt.setString(7, member.getStatus());

			re = pstmt.executeUpdate();
			System.out.println("회원 등록 완료");

		} catch (SQLException e) {
			System.out.println("회원 등록 실패");
			e.printStackTrace();
		}
		return re;
	}

	// 회원 리스트 조회
	public List<MemberVO> memberList() {
		List<MemberVO> list = new ArrayList<>();
		String sql = "SELECT * FROM member ORDER BY m_id";

		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				MemberVO member = new MemberVO();
				member.setMId(rs.getInt("m_id")); 
				member.setMPwd(rs.getString("m_pwd"));
				member.setMName(rs.getString("m_name"));
				member.setMAddr(rs.getString("m_addr"));
				member.setMJumin(rs.getString("m_jumin"));
				member.setMPhone(rs.getString("m_phone"));
				member.setJoinDate(rs.getDate("join_date").toLocalDate());
				member.setStatus(rs.getString("status"));

				list.add(member);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	//아이디 중복확인 
	public boolean isDuplicateId(int mId) {
	    String sql = "SELECT COUNT(*) FROM member WHERE m_id = ?";
	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, mId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}



	// 회원 정보 수정
	public void updateMember(MemberVO member) {
		String sql = "UPDATE member SET m_pwd=?, m_name=?, m_addr=?, m_jumin=?, m_phone=?, status=? WHERE m_id=?";

		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, member.getMPwd());
			pstmt.setString(2, member.getMName());
			pstmt.setString(3, member.getMAddr());
			pstmt.setString(4, member.getMJumin());
			pstmt.setString(5, member.getMPhone());
			pstmt.setString(6, member.getStatus());
			pstmt.setInt(7, member.getMId()); 

			pstmt.executeUpdate();
			System.out.println("회원 수정 완료");

		} catch (SQLException e) {
			System.out.println("회원 수정 실패");
			e.printStackTrace();
		}
	}

	// 회원 삭제
	public void deleteMember(int mId) {
		String sql = "DELETE FROM member WHERE m_id=?";

		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, mId);
			pstmt.executeUpdate();
			System.out.println("회원 삭제 완료");

		} catch (SQLException e) {
			System.out.println("회원 삭제 실패");
			e.printStackTrace();
		}
	}

	// 회원 검색
	public MemberVO searchMember(int mId) {
		MemberVO member = null;
		String sql = "SELECT * FROM member WHERE m_id=?";

		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, mId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				member = new MemberVO();
				member.setMId(rs.getInt("m_id"));
				member.setMPwd(rs.getString("m_pwd"));
				member.setMName(rs.getString("m_name"));
				member.setMAddr(rs.getString("m_addr"));
				member.setMJumin(rs.getString("m_jumin"));
				member.setMPhone(rs.getString("m_phone"));
				member.setJoinDate(rs.getDate("join_date").toLocalDate());
				member.setStatus(rs.getString("status"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return member;
	}

}
