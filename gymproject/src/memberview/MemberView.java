package memberview;

import java.util.Scanner;

import gym.dao.MemberDAO;
import gym.vo.MemberVO;
import loginview.LoginMember;

public class MemberView {

    public static void showMemberMenu() {
        Scanner sc = new Scanner(System.in);
        int loginId = LoginMember.loginId;

        // 로그인된 회원 정보 조회
        MemberDAO memberDAO = new MemberDAO();
        MemberVO member = memberDAO.searchMember(loginId);

        MemberSearchView searchView = new MemberSearchView();
        MemberUpdateView updateView = new MemberUpdateView();
        MemberPayView payView = new MemberPayView(); 

        while (true) {
            System.out.println("\n===== 회원 메뉴 =====");
            System.out.println("1. 내 정보 보기");
            System.out.println("2. 회원 정보 수정");
            System.out.println("3. 결제하기");
            System.out.println("4. 회원 탈퇴");
            System.out.println("5. 로그아웃");
            System.out.print("번호 선택: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                	searchView.showMyInfo();
                    break;
                case "2":
                    updateView.updateMyInfo();
                    break;
                case "3":
                    payView.insertPayView();
                    break;
                case "4":
                    MemberDeleteView.deleteMyAccountView();
                    return;
                case "5":
                    System.out.println("로그아웃되었습니다.");
                    LoginMember.loginId = -1;
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }
}
