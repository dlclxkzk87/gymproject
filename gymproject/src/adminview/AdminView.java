package adminview;

import java.util.Scanner;
import startview.StartView;

public class AdminView {

	public static void showAdminMenu() {
		Scanner sc = new Scanner(System.in);
		int menu;

		do {
			System.out.println("\n===== 관리자 메뉴 =====");
			System.out.println("1. 정보 조회");
			System.out.println("2. 정보 수정");
			System.out.println("3. 계정 삭제");
			System.out.println("4. 회원 정보 및 결제 내역 조회");
			System.out.println("0. 로그아웃");
			System.out.print("선택 >> ");
			menu = sc.nextInt();

			switch (menu) {
			case 1 -> AdminSearchView.showAdminList();
			case 2 -> AdminUpdateView.upDateAdmin();
			case 3 -> AdminDeleteView.deleteAdmin();
			case 4 -> MemberListView.showMemberInfoMenu();
			case 0 -> {
				System.out.println("로그아웃합니다.");
				StartView st = new StartView();
				st.start();
			}
			default -> System.out.println("잘못된 입력입니다.");
			}
		} while (menu != 0);
	}
}
