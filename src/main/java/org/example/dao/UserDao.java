package org.example.dao;

import org.example.model.User;
import org.example.config.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;  // JDBC의 결과 집합을 위한 클래스
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 정보를 데이터베이스에서 다루는 DAO 클래스.
 * CRUD(Create, Read, Update, Delete) 기능 제공.
 */
public class UserDao {

    /**
     * 사용자 정보를 employee 테이블에 삽입하는 메서드.
     * @param user 삽입할 사용자 정보 (name, age)
     */
    public void insert(User user) {
        String sql = "INSERT INTO employee (name, age) VALUES (?, ?)"; // ?는 값을 채우기 위한 자리표시자

        try (
                Connection conn = DBUtil.getConnection();                  // DB 연결 객체
                PreparedStatement pstmt = conn.prepareStatement(sql)      // SQL 준비
        ) {
            pstmt.setString(1, user.getName());  // 첫 번째 ?에 name 설정
            pstmt.setInt(2, user.getAge());      // 두 번째 ?에 age 설정
            pstmt.executeUpdate();               // SQL 실행 (INSERT)
        } catch (SQLException e) {
            e.printStackTrace();  // 예외 출력
        }
    }

    /**
     * employee 테이블의 모든 사용자 데이터를 조회하여 리스트로 반환하는 메서드.
     * @return 모든 사용자 목록
     */
    public List<User> getAll() {
        List<User> list = new ArrayList<>();  // 결과를 담을 리스트
        String sql = "SELECT * FROM employee";

        try (
                Connection conn = DBUtil.getConnection();                  // DB 연결
                PreparedStatement pstmt = conn.prepareStatement(sql);     // SQL 준비
                ResultSet rs = pstmt.executeQuery()                        // SQL 실행 및 결과 집합 받기
        ) {
            // 결과 집합에서 레코드를 하나씩 꺼내서 User 객체로 만들어 리스트에 추가
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age")
                );
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 주어진 사용자의 id에 해당하는 레코드의 이름(name)을 수정하는 메서드.
     * @param user 수정할 사용자 정보 (id, name)
     */
    public void updateName(User user) {
        String sql = "UPDATE employee SET name = ? WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();                 // DB 연결
                PreparedStatement pstmt = conn.prepareStatement(sql)     // SQL 준비
        ) {
            pstmt.setString(1, user.getName());  // 첫 번째 ?에 새 이름 설정
            pstmt.setInt(2, user.getId());       // 두 번째 ?에 ID 설정
            int result = pstmt.executeUpdate();  // SQL 실행 (UPDATE)

            // 업데이트 결과 확인
            if (result == 0) {
                System.out.println("해당 ID가 없습니다.");
            } else {
                System.out.println("이름이 성공적으로 수정되었습니다.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 주어진 ID를 가진 사용자 정보를 employee 테이블에서 삭제하는 메서드.
     * @param id 삭제할 사용자 ID
     */
    public void delete(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();                 // DB 연결
                PreparedStatement pstmt = conn.prepareStatement(sql)     // SQL 준비
        ) {
            pstmt.setInt(1, id);           // ?에 ID 설정
            pstmt.executeUpdate();         // SQL 실행 (DELETE)
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
