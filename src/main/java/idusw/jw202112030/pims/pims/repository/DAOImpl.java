package idusw.jw202112030.pims.pims.repository;

import java.sql.*;

public class DAOImpl implements DAO {
    @Override
    public Connection getConnection() {
        Connection conn = null;
        // DB 연결을 위한 정보, JDBC URL, User ID & Password
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_pims";
        String dbUser = "root";
        String dbPw = "1234";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); //드라이버 로딩
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPw);
            // 적재된 드라이버 관리자 객체의 getConnection() 정적메소드를 호출하여 Connection 객체 생성
            // Connection -> statement / PreparedStatement 객체 생성 -> query 실행
            // -> ResultSet (read) or 영향받은 row 수(create, update, delete) 반환
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver not found");
            e.printStackTrace();
        } catch(SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void closeResources(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs) throws SQLException {
        if(conn != null) {   conn.close(); }
        if(stmt != null) {   stmt.close(); }
        if(pstmt != null) {   pstmt.close(); }
        if(rs != null) {   rs.close(); }
    }

}
