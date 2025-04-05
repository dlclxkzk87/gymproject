package adminview;

import java.util.ArrayList;

import gym.dao.AdminDAO;
import gym.vo.AdminVO;

public class AdminSearchView {

	public static void showAdminList() {
		AdminDAO dao = new AdminDAO();
		ArrayList<AdminVO> list = dao.adminList();

		System.out.println("============================================================================");
		System.out.printf(" %-5s | %-9s | %-6s | %-11s | %-11s | %-7s \n",
				"ID", "비밀번호", "이름", "입사일", "전화번호", "급여");
		System.out.println("----------------------------------------------------------------------------");

		for (AdminVO admin : list) {
			System.out.printf(" %-5d | %-10s | %-6s | %-12s | %-13s | %-7d \n",
					admin.getaId(),
					admin.getaPwd(),
					admin.getaName(),
					admin.getHiredate(),
					admin.getaPhone(),
					admin.getSalary()
			);
		}
		System.out.println("============================================================================");
	}

}
