package memberview;

import gym.dao.MemberDAO;
import gym.vo.MemberVO;
import loginview.LoginMember;

import java.util.Scanner;

public class MemberUpdateView {

    private MemberDAO memberDAO = new MemberDAO();

    public void updateMyInfo() {
        Scanner sc = new Scanner(System.in);
        int loginId = LoginMember.loginId;

        // 현재 정보 조회
        MemberVO member = memberDAO.searchMember(loginId);
        if (member == null) {
            System.out.println("❗ 회원 정보를 찾을 수 없습니다.");
            return;
        }

        System.out.println("\n[ 내 정보 수정 ]");
        System.out.println("※ 수정하지 않을 항목은 Enter만 누르세요.");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        System.out.print("새 비밀번호 (" + member.getMPwd() + "): ");
        String pwd = sc.nextLine();
        if (!pwd.isBlank()) member.setMPwd(pwd);

        System.out.print("이름 (" + member.getMName() + "): ");
        String name = sc.nextLine();
        if (!name.isBlank()) member.setMName(name);

        System.out.print("주소 (" + member.getMAddr() + "): ");
        String addr = sc.nextLine();
        if (!addr.isBlank()) member.setMAddr(addr);

        System.out.print("연락처 (" + member.getMPhone() + "): ");
        String phone = sc.nextLine();
        if (!phone.isBlank()) member.setMPhone(phone);

        System.out.print("주민등록번호 (" + member.getMJumin() + "): ");
        String jumin = sc.nextLine();
        if (!jumin.isBlank()) member.setMJumin(jumin);

        // 정보 수정 실행
        try {
            memberDAO.updateMember(member);
            System.out.println("회원 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            System.out.println("정보 수정 중 오류 발생: " + e.getMessage());
        }
    }
}
