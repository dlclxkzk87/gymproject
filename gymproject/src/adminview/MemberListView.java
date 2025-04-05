package adminview;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import gym.dao.AdminDAO;
import gym.dao.PayDAO;
import gym.vo.PayVO;

public class MemberListView {

	private static final String DIR_PATH = "D:\\minproject\\산출물";

	public static void showMemberDetails() {
		AdminDAO dao = new AdminDAO();
		ArrayList<String> details = dao.getMemberDetails();
		for (int i = 0; i < details.size(); i++) {
			System.out.println(details.get(i));
			if (i != details.size() - 1) {
				System.out.println("--------------------------------------------------------------");
			}
		}
	}

	public static void showMemberInfoMenu() {
		Scanner sc = new Scanner(System.in);
		int menu;

		do {
			System.out.println("\n=== 회원 메뉴 ===");
			System.out.println("1. 회원정보 조회");
			System.out.println("2. 전체 결제목록 조회");
			System.out.println("3. 회원의 결제내역 조회");
			System.out.println("4. 기간별 결제내역 조회");
			System.out.println("0. 관리자 메뉴로 돌아가기");
			System.out.print("선택 >> ");
			menu = sc.nextInt();

			switch (menu) {
			case 1 -> showMemberDetails();
			case 2 -> readAllPayments();
			case 3 -> {
				System.out.print("조회할 회원 아이디를 입력하세요: ");
				int mId = sc.nextInt();
				readPaymentsByMember(mId);
			}
			case 4 -> {
				sc.nextLine(); // 버퍼 비우기
				System.out.print("시작일 (yyyy-MM-dd): ");
				String start = sc.nextLine();
				System.out.print("종료일 (yyyy-MM-dd): ");
				String end = sc.nextLine();

				if (!start.matches("\\d{8}") || !end.matches("\\d{8}")) {
			        System.out.println("❗ 날짜는 하이픈 없이 8자리(yyyyMMdd)로 입력해주세요.");
			        break;
			    }
				readPaymentsByDateRange(start, end);
			}
			case 0 -> System.out.println("관리자 메뉴로 돌아갑니다.");
			default -> System.out.println("올바른 번호를 입력해주세요.");
			}

		} while (menu != 0);
		sc.close();
	}

	private static void readAllPayments() {
		ArrayList<PayVO> list = PayDAO.readPaymentList();
		if (list.isEmpty()) {
			System.out.println("📭 결제 내역이 없습니다.");
			return;
		}

		System.out.println("전체 결제 내역:");
		printPaymentList(list);

		String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_결제내역.txt";
		savePaymentListToFile(DIR_PATH + "\\" + fileName, list);
	}

	private static void readPaymentsByMember(int mId) {
		ArrayList<PayVO> list = PayDAO.readPaymentByMemberLId(mId);
		if (list.isEmpty()) {
			System.out.println("해당 회원의 결제 내역이 없습니다.");
			return;
		}

		System.out.println("회원 결제 내역:");
		printPaymentList(list);

		String fileName = mId + "_결제내역.txt";
		savePaymentListToFile(DIR_PATH + "\\" + fileName, list);
	}

	private static void readPaymentsByDateRange(String start, String end) {
	    try {
	        String startFormatted = formatToDate(start);
	        String endFormatted = formatToDate(end);

	        LocalDateTime startDate = LocalDate.parse(startFormatted).atStartOfDay();
	        LocalDateTime endDate = LocalDate.parse(endFormatted).atTime(LocalTime.MAX);

	        ArrayList<PayVO> list = PayDAO.filterPaymentByDate(startDate, endDate);

	        if (list.isEmpty()) {
	            System.out.println("해당 기간에 결제 내역이 없습니다.");
	            return;
	        }

	        System.out.println("\n결제 내역:");
	        printPaymentList(list);

	        String fileName = start + "_" + end + "_결제내역.txt";
	        savePaymentListToFile(DIR_PATH + "\\" + fileName, list);

	    } catch (Exception e) {
	        System.out.println("날짜 처리 중 오류 발생 : " + e.getMessage());
	    }
	}

	private static String formatToDate(String date) {
		return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
	}

	private static void printPaymentList(List<PayVO> list) {
		System.out.println("---------------------------------------------------------------------");
		System.out.printf("%-5s %-5s %-20s %-10s %-6s %-6s %-6s\n", "번호", "종류", "날짜", "금액", "회원ID", "MS_ID", "PT_ID");
		System.out.println("---------------------------------------------------------------------");

		for (PayVO p : list) {
			System.out.printf("%-5d %-5s %-20s %-10d %-6d %-6s %-6s\n",
					p.getpNo(), p.getpType(), p.getpDate(), p.getpPrice(), p.getmId(),
					(p.getMsId() == 0 ? "-" : p.getMsId()), (p.getPtId() == 0 ? "-" : p.getPtId()));
		}
	}
	private static void savePaymentListToFile(String filePath, List<PayVO> list) {
		File directory = new File(new File(filePath).getParent());
		if (!directory.exists()) {
			directory.mkdirs();
		}

		try (PrintWriter writer = new PrintWriter(filePath)) {
			writer.println("번호\t종류\t날짜\t\t\t금액\t회원ID\tMS_ID\tPT_ID");
			for (PayVO p : list) {
				writer.printf("%d\t%s\t%s\t%d\t%d\t%s\t%s\n",
						p.getpNo(), p.getpType(), p.getpDate(), p.getpPrice(), p.getmId(),
						(p.getMsId() == 0 ? "-" : p.getMsId()), (p.getPtId() == 0 ? "-" : p.getPtId()));
			}
			System.out.println("결제 내역이 파일로 저장되었습니다: " + filePath);
		} catch (IOException e) {
			System.out.println("파일 저장 중 오류 발생: " + e.getMessage());
		}
	}
}
