package adminview;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import gym.dao.PayDAO;

public class AdminImportStatisticsView {
	//일/주/월 단위 통계
    public static void getPaymentStatsView() {
        Scanner sc = new Scanner(System.in);
        int statType = -1;

        while (true) {
            System.out.print("조회 기간 선택 1. 일별 2. 주별 3. 월별 ==> ");
            try {
                statType = Integer.parseInt(sc.nextLine());
                if (statType < 1 || statType > 3) {
                    System.out.println("다시 입력해주세요.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력해주세요.");
            }
        }

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        if (statType == 1) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            while (true) {
                System.out.print("조회할 날짜를 입력하세요 (yyyy-MM-dd) ==> ");
                String input = sc.nextLine();
                try {
                    LocalDate date = LocalDate.parse(input, dtf);
                    startDateTime = date.atStartOfDay();
                    endDateTime = date.atTime(23, 59, 59);
                    break;
                } catch (Exception e) {
                    System.out.println("형식이 올바르지 않습니다. 다시 입력해주세요.");
                }
            }
        } else if (statType == 2) {  // 주별: 시작 날짜와 종료 날짜 차이가 정확히 7	일(포함)이 되도록
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            while (true) {
                System.out.print("시작 날짜를 입력하세요 (yyyy-MM-dd) ==> ");
                String startInput = sc.nextLine();
                System.out.print("종료 날짜를 입력하세요 (yyyy-MM-dd) ==> ");
                String endInput = sc.nextLine();
                try {
                    LocalDate startDate = LocalDate.parse(startInput, dtf);
                    LocalDate endDate = LocalDate.parse(endInput, dtf);
                    long diff = ChronoUnit.DAYS.between(startDate, endDate);

                    if (diff != 6) {
                        System.out.println("시작일과 종료일의 차이가 7일이 아닙니다. 다시 입력해주세요.");
                        continue;
                    }
                    startDateTime = startDate.atStartOfDay();
                    endDateTime = endDate.atTime(23, 59, 59);
                    break;
                } catch (Exception e) {
                    System.out.println("형식이 올바르지 않습니다. 다시 입력해주세요.");
                }
            }
        } else if (statType == 3) {
            DateTimeFormatter dtfMonth = DateTimeFormatter.ofPattern("yyyy-MM");
            while (true) {
                System.out.print("조회할 연월을 입력하세요 (yyyy-MM) ==> ");
                String input = sc.nextLine();
                try {
                    YearMonth ym = YearMonth.parse(input, dtfMonth);
                    startDateTime = ym.atDay(1).atStartOfDay();
                    endDateTime = ym.atEndOfMonth().atTime(23, 59, 59);
                    break;
                } catch (Exception e) {
                    System.out.println("형식이 올바르지 않습니다. 다시 입력해주세요.");
                }
            }
        }
        String startDateStr = startDateTime.toLocalDate().toString();
        String endDateStr = endDateTime.toLocalDate().toString();
        System.out.println("**************************************");
        System.out.println("조회 기간 : " + startDateStr + " ~ " + endDateStr);

        HashMap<String, Integer> stats = PayDAO.getPaymentStats(statType, startDateTime, endDateTime);

        if (stats.isEmpty()) {
            System.out.println("해당 기간에 결제 내역이 없습니다.");
        } else {
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                System.out.println("총 매출 : " + entry.getValue() + "원");
            }
        }
        System.out.println("**************************************");
    }

}
