package gym.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionProvider;
import gym.vo.PtVO;

public class PtDAO {

    // PT 등록
    public int insertPt(PtVO pt) {
        int re = -1;
//        String sql = "insert into PT(pt_id, t_cnt, u_cnt, pt_type, pt_price, m_id) values(PT_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
        String sql = "insert into PT(pt_id, t_cnt, u_cnt, pt_type, pt_price, m_id) " +
                "values ((select nvl(max(pt_id), 0) + 1 from PT), ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

        	pstmt.setInt(1, pt.gettCnt());
        	pstmt.setInt(2, pt.getuCnt()); // 사용 횟수 추가
        	pstmt.setString(3, pt.getPtType());
        	int price = pt.getPtType().equals("1:1") ? 50000 : 20000;
        	pstmt.setInt(4, price);     	
        	pstmt.setInt(5, pt.getmId());

            re = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        }

        return re;
    }

    // PT 전체 목록 조회
    public List<PtVO> readPtList() {
        List<PtVO> list = new ArrayList<>();
        String sql = "select * from PT order by pt_id";

        try (Connection conn = ConnectionProvider.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new PtVO(
                    rs.getInt("pt_id"),
                    rs.getInt("t_cnt"),
                    rs.getInt("u_cnt"),
                    rs.getString("pt_type"),
                    rs.getInt("pt_price"),
                    rs.getInt("m_id")
                ));
            }

        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        }

        return list;
    }

    // 회원별 PT 정보 조회
    public List<PtVO> readPtByMemberId(int mId) {
        List<PtVO> list = new ArrayList<>();
        String sql = "select * from PT where m_id = ?";

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new PtVO(
                    rs.getInt("pt_id"),
                    rs.getInt("t_cnt"),
                    rs.getInt("u_cnt"),
                    rs.getString("pt_type"),
                    rs.getInt("pt_price"),
                    rs.getInt("m_id")
                ));
            }

        } catch (Exception e) {
            System.out.println("예외발생: " + e.getMessage());
        }

        return list;
    }

    // 잔여 PT 횟수 조회
    public Integer getRemainingPtCount(int mId) {
        String sql = "select t_cnt, u_cnt from PT where m_id = ?";
        Integer remainingCount = null;

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int tCnt = rs.getInt("t_cnt");
                int uCnt = rs.getInt("u_cnt");
                remainingCount = tCnt - uCnt;
            }

        } catch (Exception e) {
            System.out.println("잔여 PT 조회 실패: " + e.getMessage());
            e.printStackTrace();
        }

        return remainingCount;
    }

    // PT 전체 횟수 수정
    public boolean updatePtTotalCount(int mId, int newTCnt) {
        String sql = "update PT set t_cnt = ? where m_id = ?";
        boolean isUpdated = false;

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newTCnt);
            pstmt.setInt(2, mId);

            int result = pstmt.executeUpdate();
            isUpdated = result > 0;

        } catch (Exception e) {
            System.out.println("PT 전체 횟수 수정 실패: " + e.getMessage());
            e.printStackTrace();
        }

        return isUpdated;
    }

    // PT 1회 사용
    public boolean usePt(int mId) {
        String selectSql = "select u_cnt, t_cnt from PT where m_id = ?";
        String updateSql = "update PT set u_cnt = u_cnt + 1 where m_id = ?";
        boolean used = false;

        try (Connection conn = ConnectionProvider.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            selectStmt.setInt(1, mId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int uCnt = rs.getInt("u_cnt");
                int tCnt = rs.getInt("t_cnt");

                if (uCnt >= tCnt) {
                    System.out.println("잔여 PT 횟수가 없습니다.");
                    return false;
                }

                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, mId);
                    updateStmt.executeUpdate();
                    used = true;
                }
            } else {
                System.out.println("회원 정보를 찾을 수 없습니다.");
            }

        } catch (Exception e) {
            System.out.println("PT 사용 실패: " + e.getMessage());
            e.printStackTrace();
        }

        return used;
    }

    
}

