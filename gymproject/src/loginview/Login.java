package loginview;

import java.util.InputMismatchException;
import java.util.Scanner;

//회원 로그인 선택 or 관리자 로그인 선택
public class Login {
	
	public static void loginMenu() {
		Scanner sc = new Scanner(System.in);
		int choice;
		while(true) { // 1 또는 2 입력 시 까지 무한 반복
			System.out.println("\n1.회원로그인 2.관리자로그인 0.뒤로가기");
			System.out.print("==> ");
			try {
				choice = sc.nextInt();
				if(choice == 1) {
					LoginMember.loginMember();
				}else if(choice == 2) {
					LoginAdmin.loginAdmin();
				}else if(choice == 0) {
					break;
				}else {
					System.out.println("잘못된 입력값 입니다\n");
				}
			}catch(InputMismatchException e) {
				sc = new Scanner(System.in);
				System.out.println("잘못된 입력값 입니다\n");
			}	
		}
	}

}