package loginview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import db.ConnectionProvider;
import memberview.MemberView;

public class LoginMember {
	public static int loginId = -1;

	public void loginMember() {
		int id;
		String pwd;
		Scanner sc = new Scanner(System.in);
		System.out.print("회원 아이디를 입력하세요==>");
		id = sc.nextInt();
		System.out.print("회원 비밀번호를 입력하세요==>");
		pwd = sc.next();
		String sql= "select * from member where m_id=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				if(pwd.equals(rs.getString("m_pwd"))) {
					loginId = id; 
					System.out.println("로그인 성공!");
					pstmt.close();
					conn.close();
					
					// 로그인 성공 후 회원 메뉴로 이동
					MemberView.showMemberMenu();
				} else {
					System.out.println("비밀번호가 틀렸습니다.\n");
					pstmt.close();
					conn.close();
				}
			} else {
				System.out.println("존재하지 않는 회원 아이디입니다.\n");
				pstmt.close();
				conn.close();
			}					
		}catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
	}
}
