package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student){
        //todo#1 insert student

        // sql문 작성
        String sql = String.format("insert into jdbc_students(id,name,gender,age) values ('%s','%s','%s','%d')",
                student.getId(),
                student.getName(),
                student.getGender(),
                student.getAge());

        log.debug("save:{}",sql);

        // DbUtils 클래스에서 DB와 연결 메서드 가져옴
        try(Connection connection = DbUtils.getConnection();
            // 연결을 열고 statement 만들어줌
            Statement statement = connection.createStatement()
        ) {
            // 연결된 db에 쿼리문 날림
            int result = statement.executeUpdate(sql);
            log.debug("save:{}",result);
            // 쿼리 연산 결과 받아서 사용자에게 리턴
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 student 조회
        String sql = String.format("select * from jdbc_students where id='%s'",id);
        log.debug("findById:{}",sql);

        try( Connection connection = DbUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql);
             ){

            // ResultSet : 쿼리에 맞는 결과값들이 날아오고, next() 메서드로 선택되는 행을 바꿀 수 있다,
            // 한 행에서 값을 가져올 때에는 타입을 지정해 불러올 수 있다
            if(rs.next()){
                Student student = new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        Student.GENDER.valueOf(rs.getString("gender")),
                        rs.getInt("age"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                        );
                return Optional.of(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int update(Student student){
        //todo#3 student 수정, name <- 수정합니다.
        String sql = String.format("update jdbc_students set name='%s', gender='%s', age=%d where id='%s' ",
                student.getName(),student.getGender(),student.getAge(),student.getId());
        log.debug("update:{}",sql);

        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement();
        ) {
            int result = statement.executeUpdate(sql);
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(String id){
       //todo#4 student 삭제
        String sql = String.format("delete from jdbc_students where id='%s'",id);
        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement();
        ) {
            int result = statement.executeUpdate(sql);
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
