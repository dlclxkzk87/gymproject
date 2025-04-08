package memberview;

import java.util.List;
import java.util.Scanner;

import gym.dao.MemberDAO;
import gym.dao.MembershipDAO;
import gym.dao.PayDAO;
import gym.dao.PtDAO;
import gym.vo.MemberVO;
import gym.vo.PayVO;
import gym.vo.PtVO;
import loginview.LoginMember;

public class MemberPayView {

	// 결제 등록
	public static void insertPayView() {
		Scanner sc = new Scanner(System.in);

	    System.out.println("결제를 시작합니다");

	    // 1. 로그인된 회원 정보 가져오기
	    int loginId = LoginMember.loginId;

	    MemberDAO dao = new MemberDAO();
	    MemberVO member = dao.searchMember(loginId); 
	    if (member == null) {
	        System.out.println("로그인된 회원 정보를 찾을 수 없습니다.");
	        return;
	    }

	    int memberId = member.getMId(); 
	    System.out.println("회원 아이디: " + memberId);

		// 결제 방식
		int paymentMethod = -1;
		String paymentMethodStr = "";
		while (true) {
			System.out.print("결제 방식을 선택하세요 1. 카드 2. 현금 0. 종료 ==> ");
			try {
				paymentMethod = Integer.parseInt(sc.nextLine());
				if (paymentMethod == 0) {
					System.out.println("종료합니다.");
					return;
				}
				if (paymentMethod == 1) {
					paymentMethodStr = "카드";
					break;
				} else if (paymentMethod == 2) {
					paymentMethodStr = "현금";
					break;
				} else {
					System.out.println("다시 입력하세요.");
				}
			} catch (NumberFormatException e) {
				System.out.println("다시 입력하세요.");
			}
		}

		// 결제 항목 선택 (가격 산정을 위한 항목)
		int productOption = -1;
		while (true) {
			System.out.print("결제 항목을 선택하세요 1. PT 이용권 2. 멤버십 3. PT 이용권+멤버십 0. 종료 ==> ");
			try {
				productOption = Integer.parseInt(sc.nextLine());
				if (productOption == 0) {
					System.out.println("종료합니다.");
					sc.close();
					return;
				}
				if (productOption >= 1 && productOption <= 3) {
					break;
				} else {
					System.out.println("다시 입력하세요.");
				}
			} catch (NumberFormatException e) {
				System.out.println("다시 입력하세요.");
			}
		}

		String productType = "";
		int price = 0; // 최종 결제 금액
		int ptOption = 0;
		int count = 0;
		int membershipOption = 0;

		switch (productOption) {
		case 1: // PT 이용권
			productType = "PT 이용권";
			while (true) {
				System.out.print("원하는 PT종류를 선택하세요 1. 1:1  2. 1:5  0. 종료 ==> ");
				try {
					ptOption = Integer.parseInt(sc.nextLine());
					if (ptOption == 0) {
						System.out.println("종료합니다.");
						return;
					}
					if (ptOption == 1 || ptOption == 2) {
						break;
					} else {
						System.out.println("다시 입력하세요.");
					}
				} catch (NumberFormatException e) {
					System.out.println("다시 입력하세요.");
				}
			}
			while (true) {
				System.out.print("횟수를 입력하세요 (10, 20, 30) ==> ");
				try {
					count = Integer.parseInt(sc.nextLine());
					if (count == 10 || count == 20 || count == 30) {
						break;
					} else {
						System.out.println("다시 입력하세요.");
					}
				} catch (NumberFormatException e) {
					System.out.println("다시 입력하세요.");
				}
			}

			if (ptOption == 1) {
				price = count * 50000;
			} else {
				price = count * 20000;
			}
			break;

		case 2:
			productType = "멤버십";
			while (true) {
				System.out.print("멤버십 기간을 선택하세요 1. 1개월 2. 3개월 3. 6개월 0. 종료 ==> ");
				try {
					membershipOption = Integer.parseInt(sc.nextLine());
					if (membershipOption == 0) {
						System.out.println("종료합니다.");
						return;
					}
					if (membershipOption >= 1 && membershipOption <= 3) {
						break;
					} else {
						System.out.println("다시 입력하세요.");
					}
				} catch (NumberFormatException e) {
					System.out.println("다시 입력하세요.");
				}
			}
			if (membershipOption == 1) {
				price = 110000;
			} else if (membershipOption == 2) {
				price = 180000;
			} else if (membershipOption == 3) {
				price = 360000;
			}
			break;

		case 3:
			productType = "PT 이용권 + 멤버십";
			// PT
			while (true) {
				System.out.print("원하는 PT종류를 선택하세요 1. 1:1  2. 1:5  0. 종료 ==> ");
				try {
					ptOption = Integer.parseInt(sc.nextLine());
					if (ptOption == 0) {
						System.out.println("종료합니다.");
						return;
					}
					if (ptOption == 1 || ptOption == 2) {
						break;
					} else {
						System.out.println("다시 입력하세요.");
					}
				} catch (NumberFormatException e) {
					System.out.println("다시 입력하세요.");
				}
			}
			while (true) {
				System.out.print("횟수를 입력하세요 (10, 20, 30만 가능) ==> ");
				try {
					count = Integer.parseInt(sc.nextLine());
					if (count == 10 || count == 20 || count == 30) {
						break;
					} else {
						System.out.println("다시 입력하세요.");
					}
				} catch (NumberFormatException e) {
					System.out.println("다시 입력하세요.");
				}
			}
			int ptPrice = (ptOption == 1) ? (count * 50000) : (count * 20000);
			while (true) {
				System.out.print("멤버십 기간을 선택하세요 1. 1개월 2. 3개월 3. 6개월 0. 종료 ==> ");
				try {
					membershipOption = Integer.parseInt(sc.nextLine());
					if (membershipOption == 0) {
						System.out.println("종료합니다.");
						return;
					}
					if (membershipOption >= 1 && membershipOption <= 3) {
						break;
					} else {
						System.out.println("다시 입력하세요.");
					}
				} catch (NumberFormatException e) {
					System.out.println("다시 입력하세요.");
				}
			}
			int membershipPrice = 0;
			if (membershipOption == 1) {
				membershipPrice = 110000;
			} else if (membershipOption == 2) {
				membershipPrice = 180000;
			} else if (membershipOption == 3) {
				membershipPrice = 360000;
			}
			price = ptPrice + membershipPrice;
			break;
		}

		// PayVO 객체 생성 및 값 세팅
		PayVO pay = new PayVO();

		pay.setpType(paymentMethodStr);
		pay.setpPrice(price);
		pay.setmId(memberId);
		int payNo = PayDAO.insertPayment(pay);

		if (payNo > 0) {
		    System.out.println("결제 성공 (결제번호: " + payNo + ")");
		    
		    // 멤버십 등록
		    if (productOption == 2 || productOption == 3) {
		        String msType = switch (membershipOption) {
		            case 1 -> "1개월";
		            case 2 -> "3개월";
		            case 3 -> "6개월";
		            default -> "";
		        };

		        int msPrice = switch (membershipOption) {
		            case 1 -> 110000;
		            case 2 -> 180000;
		            case 3 -> 360000;
		            default -> 0;
		        };

		        int msResult = new MembershipDAO().insertMembership(msType, msPrice, memberId, payNo);
		        System.out.println(msResult > 0 ? "회원권 등록 완료" : "회원권 등록 실패");
		    }

		    // PT 등록
		    if (productOption == 1 || productOption == 3) {
		        PtDAO ptDAO = new PtDAO();
		        String ptType = (ptOption == 1) ? "1:1" : "1:5";

		        List<PtVO> ptList = ptDAO.readPtByMemberId(memberId);
		        PtVO sameTypePt = ptList.stream().filter(p -> p.getPtType().equals(ptType)).findFirst().orElse(null);

		        if (sameTypePt != null) {
		            int newTotalCnt = sameTypePt.gettCnt() + count;
		            boolean updated = ptDAO.updatePtTotalCount(memberId, ptType, newTotalCnt);
		            System.out.println(updated ? "PT 횟수 추가 완료" : "PT 업데이트 실패");
		        } else {
		            PtVO pt = new PtVO();
		            pt.settCnt(count);
		            pt.setuCnt(0);
		            pt.setPtType(ptType);
		            pt.setmId(memberId);
		            pt.setpNo(payNo); 

		            int ptResult = ptDAO.insertPt(pt);
		            System.out.println(ptResult > 0 ? "PT 등록 완료" : "PT 등록 실패");
		        }
		    }
		} else {
		    System.out.println("결제 실패");
		}
		
		
	}
}