package loginview;

import java.util.Scanner;

//회원 로그인 선택 or 관리자 로그인 선택
public class Login {
	private LoginAdmin loginAdmin = new LoginAdmin();
	private LoginMember loginMember = new LoginMember();
	
	public void loginMenu() {
		Scanner sc = new Scanner(System.in);
		int choice;
		while(true) { // 1 또는 2 입력 시 까지 무한 반복
			System.out.println("1.회원로그인 2.관리자로그인");
			System.out.print("==>");
			choice = sc.nextInt();
			if(choice == 1 || choice == 2)
				break;
		}
		if(choice == 1) {
			loginMember.loginMember();
		}else {
			loginAdmin.loginAdmin();
		}
	}

}



