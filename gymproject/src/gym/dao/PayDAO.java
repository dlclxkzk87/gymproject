package gym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import db.ConnectionProvider;
import gym.vo.MemberVO;
import gym.vo.PayVO;

public class PayDAO {

	
	//결제 등록 
	public static int insertPayment(PayVO pay) { 

//		String sql = "insert into Pay (p_no, p_type, p_date, p_price, m_id) values (pay_seq.nextval, ?, sysdate, ?, ?)";
		String sql = "insert into Pay (p_no, p_type, p_date, p_price, m_id) " +
	             "values ((select nvl(max(p_no), 0) + 1 from Pay), ?, sysdate, ?, ?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			 
			pstmt.setString(1, pay.getpType());
			pstmt.setInt(2, pay.getpPrice());
			pstmt.setInt(3, pay.getmId());
					
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("결제 성공");
			}else {
				System.out.println("결제 실패");
			}
			return result;
		} catch (SQLException e) {
			System.out.println("예외발생: " + e.getMessage());
			return 0;
		}
		
	}
	
	
	//결제 목록 조회 
	public static ArrayList<PayVO> readPaymentList(){
		ArrayList<PayVO> payList = new ArrayList<>();
		String sql = "select * from pay";

		try {
			Connection conn = ConnectionProvider.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while(rs.next()) {
                PayVO pay = new PayVO();
                pay.setpNo(rs.getInt("p_no"));
                pay.setpType(rs.getString("p_type"));
               
                Timestamp ts = rs.getTimestamp("p_date");
                if(ts != null) {
                    pay.setpDate(ts.toLocalDateTime());
                }
                pay.setpPrice(rs.getInt("p_price"));
                pay.setmId(rs.getInt("m_id"));
                payList.add(pay);

		} }catch (SQLException e) { 
			System.out.println("예외발생: " + e.getMessage());
			System.out.println("조회 실패");
		}
		
		return payList;
}
	
	//결제 조회 (아이디)
	public static ArrayList<PayVO> readPaymentByMemberLId(int mId){
		
		ArrayList<PayVO> payments = new ArrayList<>();
	    String sql = "select * from pay where m_id = ?";
	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) 
	    	{
	    	pstmt.setInt(1, mId);
	    	 try (ResultSet rs = pstmt.executeQuery()) {
	             while(rs.next()){
	                 PayVO pay = new PayVO();
	                 pay.setpNo(rs.getInt("p_no"));
	                 pay.setpType(rs.getString("p_type"));
	                 
	                 Timestamp ts = rs.getTimestamp("p_date");
	                 if (ts != null) {
	                     pay.setpDate(ts.toLocalDateTime());
	                 }
	                 
	                 pay.setpPrice(rs.getInt("p_price"));
	                 pay.setmId(rs.getInt("m_id"));
	                 
	                 payments.add(pay);
	             }
	         }
	    } catch (SQLException e) {
	    	System.out.println("예외발생: " + e.getMessage());
	    	System.out.println("조회 실패");
	    }
	    
	    return payments;
		
	}
	
	//결제일 기준 검색 통계
	public static ArrayList<PayVO> filterPaymentByDate(LocalDateTime startDate, LocalDateTime endDate){
		ArrayList<PayVO> payments = new ArrayList<PayVO>();
		String sql = "select * from pay where p_date between ? and ?";
	
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, Timestamp.valueOf(startDate));
	        pstmt.setTimestamp(2, Timestamp.valueOf(endDate));
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	             while(rs.next()){
	                 PayVO pay = new PayVO();
	                 
	                 pay.setpNo(rs.getInt("p_no"));
	                 pay.setpType(rs.getString("p_type"));
	                 
	                 Timestamp ts = rs.getTimestamp("p_date");
	                 if(ts != null) {
	                     pay.setpDate(ts.toLocalDateTime());
	                 }
	                 
	                 pay.setpPrice(rs.getInt("p_price"));
	                 pay.setmId(rs.getInt("m_id"));
	                 
	                 payments.add(pay);
	             }
	         }
	        
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
			System.out.println("조회 실패");
		}
		return payments;
	}
	
	//통계 
	public static HashMap<String, Integer> getPaymentStats(int groupType, LocalDateTime startDate, LocalDateTime endDate) {
	    HashMap<String, Integer> stats = new HashMap<>();
	    String format = "";
	    
	    // 그룹화 기준 설정
	    switch (groupType) {
	        case 1: // 일별
	            format = "YYYY-MM-DD";
	            break;
	        case 2: // 주별 (ISO 주 단위)
	            format = "IYYY-IW";
	            break;
	        case 3: // 월별
	            format = "YYYY-MM";
	            break;
	        default:
	            throw new IllegalArgumentException("1, 2, 3 중 선택해주세요.");
	    }
	    
	    String sql = "select to_char(p_date, '" + format + "') as period, sum(p_price) as total " +
	    	    "from pay " +
	    	    "where p_date between ? and ? " +
	    	    "group by to_char(p_date, '" + format + "') " +
	    	    "order by period";
	    
	    try (Connection conn = ConnectionProvider.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setTimestamp(1, Timestamp.valueOf(startDate));
	        pstmt.setTimestamp(2, Timestamp.valueOf(endDate));
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                String period = rs.getString("period");
	                int total = rs.getInt("total");
	                stats.put(period, total);
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("예외발생: " + e.getMessage());
	        System.out.println("조회 실패");
	    }   
	    return stats;
	}
}