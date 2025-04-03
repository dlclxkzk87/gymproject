package registerview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import db.ConnectionProvider;
import gym.vo.MemberVO;

public class RegisterMember {
	public static int registerInfo(MemberVO m) {
		int re = -1;
		String sql = "insert into member(m_id, m_pwd, m_phone, m_addr, "
				+ "m_jumin, join_date, status) "
				+ "values(?,?,?,?,?,?,sysdate,'이용중') ";

		try (Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);){

//			pstmt.setInt(1, m.getmId());
//			pstmt.setString(2, m.getmPwd());
//			pstmt.setString(3, m.getmName());
//			pstmt.setString(4, m.getmPhone());
//			pstmt.setString(5, m.getmAddr());
//			pstmt.setString(6, m.getmJumin());

			re = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("예외 발생: " + e.getMessage());
		}


		return re;
	}

	public static void registerMember() {

		Scanner sc = new Scanner(System.in);

		System.out.print("회원 ID를 입력하세요: ");
        int mId = sc.nextInt();
        sc.nextLine();

        System.out.print("비밀번호를 입력하세요: ");
        String mPwd = sc.nextLine();

        System.out.print("이름을 입력하세요: ");
        String mName = sc.nextLine();

        System.out.print("연락처를 입력하세요: ");
        String mPhone = sc.nextLine();

        System.out.print("주소를 입력하세요: ");
        String mAddr = sc.nextLine();

        System.out.print("주민등록번호를 입력하세요: ");
        String mJumin = sc.nextLine();


//        MemberVO member = new MemberVO(mId, mPwd, mName, mPhone, mAddr, mJumin, null, "이용중");
//        int result = registerInfo(member);

//        if(result > 0) {
//			System.out.println("회원가입이 성공적으로 완료되었습니다!");
//		} else {
//			System.out.println("회원가입에 실패했습니다.");
//		}

	}

}
