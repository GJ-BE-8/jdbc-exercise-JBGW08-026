package com.nhnacademy.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    // DriverManager와 직접적인 연결을 담당

    // 자바 어플리케이션 <-> DriverManger(Drvier for mysql, Driver for myBatis, Driver for OracleSQL...) <-> 실제 DB
    // 정해진 규격대로만 쓰면 Drvier 매니저가 알아서 sql에 맞게 번역해줌
    public static Connection getConnection() {
        Connection connection = null;
        try {
            //todo connection.
            connection = DriverManager.getConnection("jdbc:mysql://133.186.241.167:3306/nhn_academy_26","nhn_academy_26","6Ut_xX8V-DrIwjBc");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return connection;
    }

}