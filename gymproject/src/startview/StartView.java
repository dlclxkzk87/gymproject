package startview;

import java.util.Scanner;

import registerview.RegisterMember;
import registerview.RegisterAdmin;
import loginview.Login;

public class StartView {

  private Scanner sc = new Scanner(System.in);
  private RegisterMember registerMember = new RegisterMember();
  private RegisterAdmin registerAdmin = new RegisterAdmin();
  private Login loginView = new Login();

  public void start() {
    while (true) {
      System.out.println("\n━━━━━━━━━━ GYM 관리 프로그램 ━━━━━━━━━━");
      System.out.println("1. 회원가입");
      System.out.println("2. 로그인");
      System.out.println("0. 프로그램 종료");
      System.out.print("선택 >> ");

      String input = sc.nextLine();

      switch (input) {
        case "1":
          showRegisterMenu();
          break;
        case "2":
         loginView.loginMenu(); 
          break;
        case "0":
          System.out.println("이용해주셔서 감사합니다.");
          System.exit(0);
        default:
          System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
      }
    }
  }

  private void showRegisterMenu() {
    System.out.println("\n[ 회원가입 유형 선택 ]");
    System.out.println("1. 회원가입");
    System.out.println("2. 관리자 가입");
    System.out.print("선택 >> ");
    String choice = sc.nextLine();

    switch (choice) {
      case "1":
        registerMember.registerMember();
        break;
      case "2":
        registerAdmin.registerAdmin();
        break;
      default:
        System.out.println("잘못된 입력입니다.");
    }
  }
}
