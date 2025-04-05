package registerview;

import java.util.Scanner;	

import gym.dao.AdminDAO;

import gym.vo.AdminVO;

public class RegisterAdmin {
	// 관리자 등록(java)
		public static void registerAdmin() {
			AdminDAO dao = new AdminDAO();
			Scanner sc = new Scanner(System.in);

			System.out.println("아이디를 입력하세요: ");
			int aId = sc.nextInt();
			sc.nextLine();
			
			System.out.println("비밀먼호 입력하세요: ");
			String aPwd = sc.nextLine();

			System.out.println("이름을 입력하세요: ");
			String aName = sc.nextLine();
			
			System.out.println("연락처를 입력하세요: ");
			String aPhone = sc.nextLine();

			AdminVO admin = new AdminVO(aId, aPwd, aName, null, 200, aPhone);
			int result = dao.registerAdminInfo(admin);

			if(result > 0) {
				System.out.println("관리자 등록이 성공적으로 완료되었습니다!");
			} else {
				System.out.println("관리자 등록에 실패했습니다.");
			}
		}

}
