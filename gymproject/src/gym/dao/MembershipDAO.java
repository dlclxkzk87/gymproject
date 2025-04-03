package gym.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;

import db.ConnectionProvider;
import gym.vo.MembershipVO;

public class MembershipDAO {
	// 신규 회원권 등록
	public int insertMembership(String msType, int msPrice, int mId) {
		int re = -1;
		int msNo;
		int months = Integer.parseInt(msType.substring(0, 1));
		
		try {
			String sql = "select ms_no from Membership order by ms_no desc";
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				msNo = rs.getInt(1) + 1; // 안 비었으면 가장 마지막 번호에서 1 더한 수로 번호 지정
			}else {
				msNo = 1001; // 비었으면 최초 번호는 1001번
			}
			ConnectionProvider.close(conn, stmt, rs);
			
			sql = "insert into Membership values (?, ?, ?, ?, ?, ?)";
			conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, msNo);
			pstmt.setString(2, msType);
			pstmt.setInt(3, msPrice);
			if(checkMembershipExpiration(mId)) { // 유효 시
				LocalDate startDate = (membershipExpireDate(mId).toLocalDate()).plusDays(1);
				LocalDate endDate = startDate.plusMonths(months).minusDays(1);
				pstmt.setDate(4, Date.valueOf(startDate));
				pstmt.setDate(5, Date.valueOf(endDate));
			}else { // 만료 시
				LocalDate currentDate = LocalDate.now();
				pstmt.setDate(4, Date.valueOf(currentDate));
				pstmt.setDate(5, Date.valueOf(currentDate.plusMonths(months).minusDays(1))); // 현재로부터 months 개월 후의 날짜
			}
			pstmt.setInt(6, mId);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);	
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return re;
	}
	
	// 모든 회원권 조회
	public ArrayList<MembershipVO> readMembershipList() {
		ArrayList<MembershipVO> list = new ArrayList<MembershipVO>();
		String sql = "select * from Membership order by ms_no";
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				list.add(new MembershipVO(
								rs.getInt(1),		// msNo
								rs.getString(2),	// msType
								rs.getInt(3),		// msPrice
								rs.getDate(4),		// msStart
								rs.getDate(5),		// msEnd
								rs.getInt(6)));		// mId
			}
			ConnectionProvider.close(conn, stmt, rs);
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return list;
	}
	
	// 회원 아이디로 회원권 조회
	public ArrayList<MembershipVO> readMembershipById(int mId) {
		ArrayList<MembershipVO> list = new ArrayList<MembershipVO>();
		String sql = "select * from Membership where m_id = ? order by ms_no";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(new MembershipVO(
								rs.getInt(1),		// msNo
								rs.getString(2),	// msType
								rs.getInt(3),		// msPrice
								rs.getDate(4),		// msStart
								rs.getDate(5),		// msEnd
								rs.getInt(6)));		// mId
			}
			ConnectionProvider.close(conn, pstmt, rs);	
		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return list;
	}
	
	// 회원의 회원권 만료 여부 확인
	public boolean checkMembershipExpiration(int mId) { // 유효 시 true 만료 또는 존재하지 않을 시 false 반환
		Date expDate = membershipExpireDate(mId);
		if(expDate == null) {
			return false;
		}else {
			LocalDate currentDate = LocalDate.now();
			if((Date.valueOf(currentDate)).after(expDate)) {
				return false;
			}else {
				return true;
			}
		}
	}
	
	// 회원권 만료일 찾기
	private Date membershipExpireDate(int mId) {
		String sql = "select ms_end from Membership where m_id = ? order by ms_end desc";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mId);
			
			ResultSet rs = pstmt.executeQuery();
			
			Date expDate;
			if(rs.next()) {
				expDate = rs.getDate(1);
				ConnectionProvider.close(conn, pstmt, rs);
				return expDate;
			}else {
				ConnectionProvider.close(conn, pstmt, rs);
				return null;
			}

		}catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
			return null;
		}
	}
}