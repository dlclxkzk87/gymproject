package memberview;

import java.util.List;
import java.util.Scanner;

import gym.dao.MembershipDAO;
import gym.dao.PayDAO;
import gym.dao.PtDAO;
import gym.vo.MemberVO;
import gym.vo.PayVO;
import gym.vo.PtVO;

public class MemberPayView {
	   public static void insertPayView() {
	        Scanner sc = new Scanner(System.in);
	        
	        System.out.println("결제를 시작합니다 (0번: 이전 메뉴로)");
	        
	        // 회원 아이디 입력 
	        int memberId = -1;
	        while (true) {
	            System.out.print("회원 아이디를 입력해주세요 ==> ");
	            String input = sc.nextLine();
	            if (input.equals("0")) {
	                showMemberMenu();
	                return;
	            }
	            try {
	                memberId = Integer.parseInt(input);
	                break;
	            } catch (NumberFormatException e) {
	                System.out.println("숫자로 다시 입력해주세요.");
	            }
	        }
	        System.out.println("입력한 회원 아이디: " + memberId);
	        
	        // 결제 방식 선택 (0 입력 시 showMemberMenu() 호출)
	        int paymentMethod = -1;
	        String paymentMethodStr = "";
	        while (true) {
	            System.out.print("결제 방식을 선택하세요 1. 카드 2. 현금 0. 종료 ==> ");
	            String input = sc.nextLine();
	            if (input.equals("0")) {
	                showMemberMenu();
	                return;
	            }
	            try {
	                paymentMethod = Integer.parseInt(input);
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
	                System.out.println("숫자로 다시 입력해주세요.");
	            }
	        }
	        
	        // 결제 항목 선택
	        int productOption = -1;
	        while (true) {
	            System.out.print("결제 항목을 선택하세요 1. PT 이용권 2. 멤버십 3. PT 이용권+멤버십 0. 종료 ==> ");
	            String input = sc.nextLine();
	            if (input.equals("0")) {
	                showMemberMenu();
	                return;
	            }
	            try {
	                productOption = Integer.parseInt(input);
	                if (productOption >= 1 && productOption <= 3) {
	                    break;
	                } else {
	                    System.out.println("다시 입력하세요.");
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("숫자로 다시 입력해주세요.");
	            }
	        }
	        
	        String productType = ""; 
	        int price = 0;            // 최종 결제 금액
	        int ptOption = 0;         
	        int count = 0;            
	        int membershipOption = 0; 
	        
	        switch (productOption) {
	            case 1: // PT 이용권
	                productType = "PT 이용권";
	                while (true) {
	                    System.out.print("원하는 PT종류를 선택하세요 1. 1:1  2. 1:5  0. 종료 ==> ");
	                    String input = sc.nextLine();
	                    if (input.equals("0")) {
	                        showMemberMenu();
	                        return;
	                    }
	                    try {
	                        ptOption = Integer.parseInt(input);
	                        if (ptOption == 1 || ptOption == 2) {
	                            break;
	                        } else {
	                            System.out.println("다시 입력하세요.");
	                        }
	                    } catch (NumberFormatException e) {
	                        System.out.println("숫자로 다시 입력해주세요.");
	                    }
	                }
	                while (true) {
	                    System.out.print("횟수를 입력하세요 (10, 20, 30) ==> ");
	                    String input = sc.nextLine();
	                    if (input.equals("0")) {
	                        showMemberMenu();
	                        return;
	                    }
	                    try {
	                        count = Integer.parseInt(input);
	                        if (count == 10 || count == 20 || count == 30) {
	                            break;
	                        } else {
	                            System.out.println("다시 입력하세요.");
	                        }
	                    } catch (NumberFormatException e) {
	                        System.out.println("숫자로 다시 입력해주세요.");
	                    }
	                }
	                if (ptOption == 1) {
	                    price = count * 50000;
	                } else {
	                    price = count * 20000;
	                }
	                break;
	                
	            case 2: // 멤버십
	                productType = "멤버십";
	                while (true) {
	                    System.out.print("멤버십 기간을 선택하세요 1. 1개월 2. 3개월 3. 6개월 0. 종료 ==> ");
	                    String input = sc.nextLine();
	                    if (input.equals("0")) {
	                        showMemberMenu();
	                        return;
	                    }
	                    try {
	                        membershipOption = Integer.parseInt(input);
	                        if (membershipOption >= 1 && membershipOption <= 3) {
	                            break;
	                        } else {
	                            System.out.println("다시 입력하세요.");
	                        }
	                    } catch (NumberFormatException e) {
	                        System.out.println("숫자로 다시 입력해주세요.");
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
	                
	            case 3: // PT 이용권 + 멤버십
	                productType = "PT 이용권 + 멤버십";
	                // PT 입력
	                while (true) {
	                    System.out.print("원하는 PT종류를 선택하세요 1. 1:1  2. 1:5  0. 종료 ==> ");
	                    String input = sc.nextLine();
	                    if (input.equals("0")) {
	                        showMemberMenu();
	                        return;
	                    }
	                    try {
	                        ptOption = Integer.parseInt(input);
	                        if (ptOption == 1 || ptOption == 2) {
	                            break;
	                        } else {
	                            System.out.println("다시 입력하세요.");
	                        }
	                    } catch (NumberFormatException e) {
	                        System.out.println("숫자로 다시 입력해주세요.");
	                    }
	                }
	                while (true) {
	                    System.out.print("횟수를 입력하세요 (10, 20, 30만 가능) ==> ");
	                    String input = sc.nextLine();
	                    if (input.equals("0")) {
	                        showMemberMenu();
	                        return;
	                    }
	                    try {
	                        count = Integer.parseInt(input);
	                        if (count == 10 || count == 20 || count == 30) {
	                            break;
	                        } else {
	                            System.out.println("다시 입력하세요.");
	                        }
	                    } catch (NumberFormatException e) {
	                        System.out.println("숫자로 다시 입력해주세요.");
	                    }
	                }
	                int ptPrice = (ptOption == 1) ? (count * 50000) : (count * 20000);
	                // 멤버십 입력
	                while (true) {
	                    System.out.print("멤버십 기간을 선택하세요 1. 1개월 2. 3개월 3. 6개월 0. 종료 ==> ");
	                    String input = sc.nextLine();
	                    if (input.equals("0")) {
	                        showMemberMenu();
	                        return;
	                    }
	                    try {
	                        membershipOption = Integer.parseInt(input);
	                        if (membershipOption >= 1 && membershipOption <= 3) {
	                            break;
	                        } else {
	                            System.out.println("다시 입력하세요.");
	                        }
	                    } catch (NumberFormatException e) {
	                        System.out.println("숫자로 다시 입력해주세요.");
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
	        
	        int result = PayDAO.insertPayment(pay);
	        
	        // 결제 정보 저장 성공 시, PT 또는 멤버십 저장
	        if (result > 0) {
	            System.out.println("결제 금액: " + price);
	    
	            // 멤버십 저장
	            if (productOption == 2 || productOption == 3) {
	                String msType = "";
	                if (membershipOption == 1) {
	                    msType = "1개월";
	                } else if (membershipOption == 2) {
	                    msType = "3개월";
	                } else if (membershipOption == 3) {
	                    msType = "6개월";
	                }
	    
	                int msPrice = 0;
	                if (membershipOption == 1) msPrice = 110000;
	                else if (membershipOption == 2) msPrice = 180000;
	                else if (membershipOption == 3) msPrice = 360000;
	    
	                int msResult = new MembershipDAO().insertMembership(msType, msPrice, memberId);
	                if (msResult > 0) {
	                    System.out.println("회원권 등록 완료");
	                } else {
	                    System.out.println("회원권 등록 실패");
	                }
	            }
	            
	            // PT 저장
	            if (productOption == 1 || productOption == 3) {
	                String ptType = (ptOption == 1) ? "1:1" : "1:5";
	                List<PtVO> existingPtList = new PtDAO().readPtByMemberId(memberId);
	    
	                if (!existingPtList.isEmpty()) {
	                    PtVO existingPt = existingPtList.get(0);
	                    int newTotalCnt = existingPt.gettCnt() + count;
	                    boolean updated = new PtDAO().updatePtTotalCount(memberId, newTotalCnt);
	    
	                    if (updated) {
	                        System.out.println("PT 횟수 추가 완료");
	                    } else {
	                        System.out.println("PT 업데이트 실패");
	                    }
	                } else {
	                    PtVO pt = new PtVO();
	                    pt.settCnt(count);
	                    pt.setuCnt(0);
	                    pt.setPtType(ptType);
	                    pt.setmId(memberId);
	    
	                    int ptResult = new PtDAO().insertPt(pt);
	                    if (ptResult > 0) {
	                        System.out.println("PT 등록 완료");
	                    } else {
	                        System.out.println("PT 등록 실패");
	                    }
	                }
	            } 
	        } else {
	            System.out.println("결제 실패");
	        } 
	        
	        sc.close();
	    }
	    
	    // 예시로 작성한 showMemberMenu() 메소드
	    public static void showMemberMenu() {
	        System.out.println("회원 메뉴로 돌아갑니다.");
	        // 실제 회원 메뉴 로직 호출
	    }
	}