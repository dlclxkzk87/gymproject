package gym.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import db.ConnectionProvider;
import gym.vo.MembershipVO;

public class MembershipDAO {

    // 회원권 등록
    public int insertMembership(String msType, int msPrice, int mId, int payNo) {
        int re = -1;
        int msId;
        int months = Integer.parseInt(msType.substring(0, 1)); // "1개월" → 1

        try (Connection conn = ConnectionProvider.getConnection()) {

            // 다음 회원권 번호 조회
            String sql = "SELECT NVL(MAX(ms_no), 1000) + 1 FROM membership";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                rs.next();
                msId = rs.getInt(1);
            }

            // 시작일과 종료일 계산
            LocalDate startDate;
            if (checkMembershipExpiration(mId)) {
                startDate = membershipExpireDate(mId).toLocalDate().plusDays(1);
            } else {
                startDate = LocalDate.now();
            }
            LocalDate endDate = startDate.plusMonths(months).minusDays(1);

            // INSERT 수행
            sql = "INSERT INTO membership (ms_no, ms_type, ms_price, ms_start, ms_end, m_id, p_no) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, msId);
                pstmt.setString(2, msType);
                pstmt.setInt(3, msPrice);
                pstmt.setDate(4, Date.valueOf(startDate));
                pstmt.setDate(5, Date.valueOf(endDate));
                pstmt.setInt(6, mId);
                pstmt.setInt(7, payNo);

                re = pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("회원권 등록 오류: " + e.getMessage());
        }

        return re;
    }

    // 전체 회원권 목록 조회
    public ArrayList<MembershipVO> readMembershipList() {
        ArrayList<MembershipVO> list = new ArrayList<>();
        String sql = "SELECT * FROM membership ORDER BY ms_no";

        try (Connection conn = ConnectionProvider.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new MembershipVO(
                        rs.getInt("ms_no"),
                        rs.getString("ms_type"),
                        rs.getInt("ms_price"),
                        rs.getDate("ms_start"),
                        rs.getDate("ms_end"),
                        rs.getInt("p_no"),
                        rs.getInt("m_id")
                ));
            }

        } catch (Exception e) {
            System.out.println("회원권 전체 조회 오류: " + e.getMessage());
        }

        return list;
    }

    // 회원 ID로 회원권 조회
    public ArrayList<MembershipVO> readMembershipById(int mId) {
        ArrayList<MembershipVO> list = new ArrayList<>();
        String sql = "SELECT * FROM membership WHERE m_id = ? ORDER BY ms_no";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new MembershipVO(
                        rs.getInt("ms_no"),
                        rs.getString("ms_type"),
                        rs.getInt("ms_price"),
                        rs.getDate("ms_start"),
                        rs.getDate("ms_end"),
                        rs.getInt("p_no"),
                        rs.getInt("m_id")
                ));
            }

        } catch (Exception e) {
            System.out.println("회원권 개별 조회 오류: " + e.getMessage());
        }

        return list;
    }

    // 회원의 회원권 만료 여부 확인
    public boolean checkMembershipExpiration(int mId) {
        Date expDate = membershipExpireDate(mId);
        return expDate != null && !LocalDate.now().isAfter(expDate.toLocalDate());
    }

    // 가장 최근 회원권 만료일 조회
    private Date membershipExpireDate(int mId) {
        String sql = "SELECT ms_end FROM membership WHERE m_id = ? ORDER BY ms_end DESC";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDate("ms_end");
            }

        } catch (Exception e) {
            System.out.println("회원권 만료일 조회 오류: " + e.getMessage());
        }

        return null;
    }
}
