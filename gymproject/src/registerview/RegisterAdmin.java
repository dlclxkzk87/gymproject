package registerview;

import java.util.Scanner;

import gym.dao.AdminDAO;
import gym.vo.AdminVO;

public class RegisterAdmin {
	// 관리자 등록
	public static void registerAdmin() {
		AdminDAO dao = new AdminDAO();
		Scanner sc = new Scanner(System.in);

		// 아이디 입력 (10~99 숫자만 허용)
		int aId = -1;
		while (true) {
		    System.out.print("아이디를 입력하세요 (10 ~ 99 숫자만 가능): ");
		    String input = sc.nextLine();
		    try {
		        aId = Integer.parseInt(input);
		        if (aId < 10 || aId > 99) {
		            System.out.println("10 이상 99 이하의 숫자만 입력 가능합니다.");
		            continue;
		        }

		        if (dao.isDuplicateId(aId)) {
		            System.out.println("이미 사용 중인 아이디입니다. 다른 번호를 입력해주세요.");
		            continue;
		        }

		        break; 
		    } catch (NumberFormatException e) {
		        System.out.println("숫자만 입력 가능합니다.");
		    }
		}


		// 비밀번호 입력 (영문+숫자 조합)
		String aPwd;
		while (true) {
			System.out.print("비밀번호를 입력하세요 (영문+숫자 조합): ");
			aPwd = sc.nextLine();
			if (aPwd.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,}$")) break;
			else System.out.println("영문+숫자를 조합해 주세요. 예: admin1234");
		}

		// 이름 입력 (한글만 허용)
		String aName;
		while (true) {
			System.out.print("이름을 입력하세요 (한글만): ");
			aName = sc.nextLine();
			if (aName.matches("^[가-힣]+$")) break;
			else System.out.println("한글 이름만 입력 가능합니다.");
		}

		// 연락처 입력 (형식: 010-0000-0000)
		String aPhone;
		while (true) {
			System.out.print("연락처를 입력하세요 (예: 010-1234-5678): ");
			aPhone = sc.nextLine();
			if (aPhone.matches("^010-\\d{4}-\\d{4}$")) break;
			else System.out.println("형식에 맞춰 입력하세요. 예: 010-1234-5678");
		}

		AdminVO admin = new AdminVO(aId, aPwd, aName, null, 200, aPhone);
		int result = dao.registerAdminInfo(admin);

		if (result > 0) {
			System.out.println("관리자 등록이 성공적으로 완료되었습니다!");
		} else {
			System.out.println("관리자 등록에 실패했습니다.");
		}
	}
}
