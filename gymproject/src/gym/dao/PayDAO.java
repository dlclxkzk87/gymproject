package gym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import db.ConnectionProvider;
import gym.vo.PayVO;

public class PayDAO {
	
	//결제 등록 
	public static int insertPayment(PayVO pay) { 
	    Scanner sc = new Scanner(System.in);
	    String pType = "";
	    int pPrice = 0;
	    int mId = 0;
	    int msId = 0;
	    int ptId = 0;

	    System.out.println("결제를 시작합니다.");

	    while (true) {
	        System.out.print("결제 종류를 선택하세요 1. 카드 | 2. 현금 ==> ");
	        try {
	            int typeInput = Integer.parseInt(sc.nextLine());
	            
	            if (typeInput == 1) {
	                pType = "카드";
	                break;
	            } else if (typeInput == 2) {
	                pType = "현금";
	                break;
	            } else {
	                System.out.println("1 또는 2 중에서 선택해주세요.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("잘못된 값입니다.");
	        }
	    }

	    // 결제 금액 입력
	    while (true) {
	        System.out.print("결제 금액: ");
	        try {
	            pPrice = Integer.parseInt(sc.nextLine());
	            if (pPrice < 0) throw new NumberFormatException(); // 음수 방지
	            break;
	        } catch (NumberFormatException e) {
	            System.out.println("❗ 숫자로 다시 입력하세요.");
	        }
	    }

	    // 회원 아이디 입력
	    while (true) {
	        System.out.print("회원 아이디: ");
	        try {
	            mId = Integer.parseInt(sc.nextLine());
	            break;
	        } catch (NumberFormatException e) {
	            System.out.println("❗ 숫자로 다시 입력하세요.");
	        }
	    }

	    // 회원권 아이디 입력 (0 가능)
	    while (true) {
	        System.out.print("회원권 아이디 (없으면 0): ");
	        try {
	            msId = Integer.parseInt(sc.nextLine());
	            break;
	        } catch (NumberFormatException e) {
	            System.out.println("❗ 숫자로 다시 입력하세요.");
	        }
	    }

	    // PT 아이디 입력 (0 가능)
	    while (true) {
	        System.out.print("PT 아이디 (없으면 0): ");
	        try {
	            ptId = Integer.parseInt(sc.nextLine());
	            break;
	        } catch (NumberFormatException e) {
	            System.out.println("❗ 숫자로 다시 입력하세요.");
	        }
	    }
		String sql = "insert into Pay (p_no, p_type, p_date, p_price, m_id, ms_id, pt_id) values (pay_seq.nextval, ?, sysdate, ?, ?, ?, ?)";
		
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			 
			pstmt.setString(1, pay.getpType());
			pstmt.setInt(2, pay.getpPrice());
			pstmt.setInt(3, pay.getmId());
			
			//ms 없는 경우 
			if(pay.getMsId() != 0) {
				pstmt.setInt(4, pay.getMsId());
			}else {
				pstmt.setNull(4, java.sql.Types.INTEGER);
			}
			
			//pt 없는 경우 
			if(pay.getPtId() != 0) {
				pstmt.setInt(5, pay.getPtId());
			}else {
				pstmt.setNull(5, java.sql.Types.INTEGER);
			}
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("결제 성공");
			}else {
				System.out.println("결제 실패");
			}
			return result;
		} catch (SQLException e) {
			System.out.println("예외처리: " + e.getMessage());
			System.out.println("결제 실패 ");
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
                pay.setMsId(rs.getInt("ms_id"));
                pay.setPtId(rs.getInt("pt_id"));
                payList.add(pay);

		} }catch (SQLException e) { 
			System.out.println("예외처리: " + e.getMessage());
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
	                 pay.setMsId(rs.getInt("ms_id"));
	                 pay.setPtId(rs.getInt("pt_id"));
	                 
	                 payments.add(pay);
	             }
	         }
	    } catch (SQLException e) {
	    	System.out.println("예외처리: " + e.getMessage());
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
	                 pay.setMsId(rs.getInt("ms_id"));
	                 pay.setPtId(rs.getInt("pt_id"));
	                 
	                 payments.add(pay);
	             }
	         }
	        
		} catch (Exception e) {
			System.out.println("예외처리: " + e.getMessage());
			System.out.println("조회 실패");
		}
		return payments;
	}
	
	//통계 
	public static HashMap<String, Integer> getPaymentStats(int groupType){
		HashMap<String, Integer> stats = new HashMap<>();
		String format = "";
		
		//1.일별, 2.주별, 3.월별
		switch (groupType) {
        case 1:
        	format = "YYYY-MM-DD";
            break;
        case 2:
        	format = "IYYY-IW";
            break;
        case 3:
        	format = "YYYY-MM";
            break;
        default:
            throw new IllegalArgumentException("다시 선택해주세요");
    }
		
	String sql = 
			"select to_char(p_date, '" + format + "') as period, sum(p_price) as total from pay group by to_char(p_date, '" + format + "') order by period";
	try {
		Connection conn = ConnectionProvider.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
	} catch (Exception e) {
		System.out.println("예외처리: " + e.getMessage());
		System.out.println("조회 실패");
		}
	return stats;
	}


}