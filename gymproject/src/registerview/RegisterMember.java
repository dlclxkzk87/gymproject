package registerview;

import java.util.Scanner;

import gym.dao.MemberDAO;
import gym.vo.MemberVO;

public class RegisterMember {

	public static void registerMember() {
	    MemberDAO dao = new MemberDAO();
	    Scanner sc = new Scanner(System.in);

	    int mId = -1;
	    while (true) {
	        System.out.print("아이디를 입력하세요 (100~999): ");
	        try {
	            mId = Integer.parseInt(sc.nextLine());
	            if (mId < 100 || mId > 999) {
	                System.out.println("100 이상 999 이하의 숫자만 입력하세요.");
	                continue;
	            }

	            if (dao.isDuplicateId(mId)) {
	                System.out.println("이미 사용 중인 아이디입니다. 다른 번호를 입력해주세요.");
	                continue;
	            }

	            break; // 유효한 아이디이고 중복 아님
	        } catch (NumberFormatException e) {
	            System.out.println("숫자만 입력하세요.");
	        }
	    }


	    String mPwd;
	    while (true) {
	        System.out.print("비밀번호를 입력하세요 (영문+숫자 조합): ");
	        mPwd = sc.nextLine();
	        if (mPwd.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,}$")) break;
	        else System.out.println("영문+숫자를 조합해 주세요. (예: qwer1234)");
	    }

	    String mName;
	    while (true) {
	        System.out.print("이름을 입력하세요 (한글만): ");
	        mName = sc.nextLine();
	        if (mName.matches("^[가-힣]+$")) break;
	        else System.out.println("한글 이름만 입력 가능합니다.");
	    }

	    String mPhone;
	    while (true) {
	        System.out.print("연락처를 입력하세요 (예: 010-1234-5678): ");
	        mPhone = sc.nextLine();
	        if (mPhone.matches("^010-\\d{4}-\\d{4}$")) break;
	        else System.out.println("형식에 맞춰 입력하세요. 예) 010-1234-5678");
	    }

	    System.out.print("주소를 입력하세요: ");
	    String mAddr = sc.nextLine();

	    String mJumin;
	    while (true) {
	        System.out.print("주민등록번호를 입력하세요 (예: 9701011234567): ");
	        mJumin = sc.nextLine();
	        if (isValidJumin(mJumin)) break;
	        else System.out.println("올바르지 않은 주민등록번호입니다. 다시 입력해 주세요.");
	    }

	    MemberVO member = new MemberVO(mId, mPwd, mName, mPhone, mAddr, mJumin, null, "이용중");
	    int result = dao.insertMember(member);

	    if (result > 0) {
	        System.out.println("회원가입이 성공적으로 완료되었습니다!");
	    } else {
	        System.out.println("회원가입에 실패했습니다.");
	    }
	}
	
	public static boolean isValidJumin(String jumin) {
	    if (!jumin.matches("^\\d{13}$")) return false;

	    int[] weights = {2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5};
	    int sum = 0;

	    for (int i = 0; i < 12; i++) {
	        sum += Character.getNumericValue(jumin.charAt(i)) * weights[i];
	    }

	    int checkDigit = (11 - (sum % 11)) % 10;

	    return checkDigit == Character.getNumericValue(jumin.charAt(12));
	}


}
