package registerview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import db.ConnectionProvider;
import gym.dao.AdminDAO;
import gym.dao.MemberDAO;
import gym.vo.MemberVO;

public class RegisterMember {
	

	public static void registerMember() {
		MemberDAO dao = new MemberDAO();
		Scanner sc = new Scanner(System.in);

		System.out.print("아이디를 입력하세요: ");
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

        MemberVO member = new MemberVO(mId, mPwd, mName, mPhone, mAddr, mJumin, null, "이용중");
        int result = dao.insertMember(member);

        if(result > 0) {
			System.out.println("회원가입이 성공적으로 완료되었습니다!");
		} else {
			System.out.println("회원가입에 실패했습니다.");
		}

	}

}
