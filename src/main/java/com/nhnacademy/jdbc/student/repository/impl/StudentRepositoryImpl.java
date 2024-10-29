package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public int save(Connection connection, Student student){
        //todo#2 학생등록
        String sql = "insert into jdbc_students(id,name,gender,age) values (?,?,?,?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1,student.getId());
            statement.setString(2,student.getName());
            statement.setString(3,student.getGender().toString());
            statement.setInt(4,student.getAge());

            int result = statement.executeUpdate(sql);
            log.debug("sql:{}",sql);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public Optional<Student> findById(Connection connection,String id){
        //todo#3 학생조회
        String sql = "selectc * from jdbc_students where id=?";
        log.debug("findById:{}",sql);

        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,id);
            rs = statement.executeQuery();
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
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public int update(Connection connection,Student student){
        //todo#4 학생수정
        String sql = "update jdbc_students set name=?, gender=?, age=? where id=?";
        log.debug("update:{}",sql);

        try(PreparedStatement statement = connection.prepareStatement(sql)) {

            

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public int deleteById(Connection connection,String id){
        //todo#5 학생삭제

        return 0;
    }

}