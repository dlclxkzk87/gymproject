package memberview;

import gym.dao.MemberDAO;
import gym.dao.MembershipDAO;
import gym.dao.PtDAO;
import gym.vo.MemberVO;
import gym.vo.MembershipVO;
import gym.vo.PtVO;
import loginview.LoginMember;

import java.sql.Date;
import java.util.List;

public class MemberSearchView {

    private MemberDAO memberDAO = new MemberDAO();
    private MembershipDAO membershipDAO = new MembershipDAO();
    private PtDAO ptDAO = new PtDAO();

    public void showMyInfo() {
        System.out.println("\n[ 나의 회원 정보 조회 ]");

        int loginId = LoginMember.loginId;
        MemberVO member = memberDAO.searchMember(loginId);

        if (member == null) {
            System.out.println("회원 정보를 찾을 수 없습니다.");
            return;
        }

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("회원 ID       : " + member.getMId());
        System.out.println("이름          : " + member.getMName());
        System.out.println("주소          : " + member.getMAddr());
        System.out.println("연락처        : " + member.getMPhone());
        System.out.println("주민등록번호  : " + member.getMJumin());
        System.out.println("가입일        : " + member.getJoinDate());
        System.out.println("상태          : " + member.getStatus());

        // 멤버십 정보 조회
        List<MembershipVO> memberships = membershipDAO.readMembershipById(loginId);  

        if (!memberships.isEmpty()) {
            Date start = memberships.get(0).getMsStart();
            Date end = memberships.get(0).getMsEnd();

            for (MembershipVO m : memberships) {
                if (m.getMsStart().before(start)) start = m.getMsStart();
                if (m.getMsEnd().after(end)) end = m.getMsEnd();
            }

            System.out.println("━━━━━━━━━━ [멤버십 정보] ━━━━━━━━━━");
            System.out.printf("총 등록 개수: %d개\n", memberships.size());
            System.out.printf("이용 기간   : %s ~ %s\n", start, end);
        } else {
            System.out.println("멤버십 정보 없음");
        }

        // PT 정보 조회
        List<PtVO> ptList = ptDAO.readPtByMemberId(loginId);

        if (!ptList.isEmpty()) {
            int totalT = 0;
            int totalU = 0;

            for (PtVO pt : ptList) {
                totalT += pt.gettCnt();
                totalU += pt.getuCnt();
            }

            System.out.println("━━━━━━━━━━━ [PT 정보] ━━━━━━━━━━━");
            System.out.printf("총 등록 횟수 : %d회\n", totalT);
            System.out.printf("총 사용 횟수 : %d회\n", totalU);
            System.out.printf("잔여 횟수    : %d회\n", totalT - totalU);
        } else {
            System.out.println("PT 등록 정보 없음");
        }
    }
}
