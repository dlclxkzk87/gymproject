package memberview;

import java.util.Scanner;

import gym.dao.MemberDAO;
import gym.vo.MemberVO;
import loginview.LoginMember;

public class MemberDeleteView {

    public static void deleteMyAccountView() {
        Scanner sc = new Scanner(System.in);
        MemberDAO dao = new MemberDAO();

        int loginId = LoginMember.loginId; 

        // 1. 로그인된 회원 정보 조회
        MemberVO member = dao.searchMember(loginId); 
        if (member == null) {
            System.out.println("로그인된 회원 정보를 찾을 수 없습니다.");
            return;
        }

        // 2. 삭제 확인
        System.out.println("정말 탈퇴하시겠습니까, " + member.getMName() + "님? (y/n): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            try {
                dao.deleteMember(loginId); 
                System.out.println("회원 탈퇴가 완료되었습니다.");
                LoginMember.loginId = -1; 
            } catch (Exception e) {
                System.out.println("탈퇴 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("탈퇴가 취소되었습니다.");
        }

    }
}
