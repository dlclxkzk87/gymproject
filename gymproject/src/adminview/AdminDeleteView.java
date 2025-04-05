package adminview;

import java.util.Scanner;

import gym.dao.AdminDAO;

public class AdminDeleteView {
	// 관리자 정보 삭제(java)
	public static void deleteAdmin() {
		AdminDAO admindao = new AdminDAO();
		Scanner sc = new Scanner(System.in);

		System.out.print("삭제할 관리자 아이디를 입력하세요: ");
        int aId = sc.nextInt();

        int result = admindao.deleteAdminInfo(aId);

        if (result > 0) {
            System.out.println("관리자 정보가 성공적으로 삭제되었습니다!");
        } else {
            System.out.println("관리자 삭제에 실패했습니다. 아이디를 다시 확인하세요.");
        }
        sc.close();
	}
}
