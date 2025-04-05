package adminview;

import java.util.Scanner;

import gym.dao.AdminDAO;
import gym.vo.AdminVO;

public class AdminUpdateView {
	// 관리자 정보 수정(java)
		public static void upDateAdmin() {
			AdminDAO admindao = new AdminDAO();
			Scanner sc = new Scanner(System.in);
	        System.out.print("수정할 관리자 아이디를 입력하세요: ");
	        int aId = sc.nextInt();
	        sc.nextLine(); // 버퍼 비우기

	        System.out.print("새 비밀번호를 입력하세요: ");
	        String Pwd = sc.nextLine();

	        System.out.print("새 연락처를 입력하세요: ");
	        String aPhone = sc.nextLine();

	        System.out.print("새 급여를 입력하세요: ");
	        int salary = sc.nextInt();

	        AdminVO admin = new AdminVO(aId, Pwd, null, null, salary, aPhone);

	        int result = admindao.upDateAdminById(admin);

	        if (result > 0) {
	            System.out.println("관리자 정보가 성공적으로 수정되었습니다!");
	        } else {
	            System.out.println("관리자 정보 수정에 실패했습니다.");
	        }
	        sc.close();
		}

}
