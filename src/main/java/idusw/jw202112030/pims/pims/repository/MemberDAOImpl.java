package idusw.jw202112030.pims.pims.repository;

import idusw.jw202112030.pims.pims.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl extends DAOImpl implements MemberDAO {
    // JDBC Objects 선언
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    // 생성자 - getConnection 메소드 호출, 연결 객체 반환
    public MemberDAOImpl() {conn = getConnection();}

    @Override
    public int create(Member member) { // 등록 : register, post
        int ret = 0;
        String sql = "INSERT INTO member(email, username, pw, phone, address, admin) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            if (conn == null) { // Connection 객체가 null인지 확인
                throw new SQLException("Database connection is not established.");
            }
            pstmt = conn.prepareStatement(sql); // prepared Statement 객체, 미리 컴파일

            pstmt.setString(1, member.getEmail());
            pstmt.setString(2, member.getUsername());
            pstmt.setString(3, member.getPw());
            pstmt.setString(4, member.getPhone());
            pstmt.setString(5, member.getAddress());
            pstmt.setInt(6, 0);

            ret = pstmt.executeUpdate(); // insert, update, delete 문 실행시 사용
        } catch (SQLException e) {
            System.err.println("Error during create operation: " + e.getMessage());
            e.printStackTrace();
        }
        return ret; // 질의의 영향을 받은 row의 수 반환, 0이면 오류, 1이상이면 정상
    }

    // ResultSet(DB 처리 결과 집합)을 Member 객체로 변환
    private Member setMemberRs(ResultSet rs) throws SQLException {
        Member retMember = new Member();
        retMember.setSeq(rs.getInt("seq"));
        retMember.setEmail(rs.getString("email"));
        retMember.setPw(rs.getString("pw"));
        retMember.setUsername(rs.getString("username"));
        retMember.setPhone(rs.getString("phone"));
        retMember.setAddress(rs.getString("address"));
        retMember.setAdmin(rs.getInt("admin"));

        return retMember;
    }

    @Override
    public Member read(Member member) {
        Member retMember = null;
        String sql = "select * from member where email=? and pw=?"; // 유일키로(unique key)로 조회
        try {
            pstmt = conn.prepareStatement(sql); // prepared Statement 객체, 미리 컴파일
            pstmt.setString(1, member.getEmail());
            pstmt.setString(2, member.getPw());

            rs = pstmt.executeQuery(); // select 문 실행 시 executeQuery 사용
            if(rs.next()) { // rs.next()는 반환된 객체에 속한 요소가 있는지를 반환하고, 다름 요소로 접근
                retMember = setMemberRs(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retMember;
    }

    @Override
    public List<Member> readList() { // read  유사
        ArrayList<Member> memberList = null;
        String sql = "select * from member where admin=0";
        try{
            stmt = conn.createStatement();  // Statement 객체, SQL 문법에 맞게(문자열화) 사용
            if((rs = stmt.executeQuery(sql)) != null){
                memberList = new ArrayList<Member>();
                while(rs.next()) {
                    Member member = new Member();
                    member = setMemberRs(rs);
                    memberList.add(member); // element 집합체 List
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return memberList;
    }

    @Override
    public int update(Member member) { // create 유사
        int ret = 0;
        String sql = "update member set username=?, pw=?, phone=?, address=? where email=?";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getUsername());
            pstmt.setString(2, member.getPw());
            pstmt.setString(3, member.getPhone());
            pstmt.setString(4, member.getAddress());
            pstmt.setString(5, member.getEmail());
            ret = pstmt.executeUpdate(); // sql 문 실행 시 사용됨
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret; // 질의의 영향을 받은 row 수 반환, 0 - 오류, 1이상 - 정상
    }

    @Override
    public int delete(Member member) {
        int ret = 0;
        String sql = "delete from member where seq=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, member.getSeq());
            ret = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    // MemberDAOImpl.java

    public Member readBySeq(int seq) {
        Member retMember = null;
        String sql = "select * from member where seq=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, seq);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                retMember = setMemberRs(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retMember;
    }
}
