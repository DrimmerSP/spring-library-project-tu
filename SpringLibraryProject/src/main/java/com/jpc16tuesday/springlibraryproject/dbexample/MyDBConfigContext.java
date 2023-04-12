package com.jpc16tuesday.springlibraryproject.dbexample;

import com.jpc16tuesday.springlibraryproject.dbexample.dao.BookDAOBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.jpc16tuesday.springlibraryproject.dbexample.constants.DBConstants.*;

@Configuration
public class MyDBConfigContext {

    @Bean
    @Scope("singleton")
    public Connection newConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://" + DB_HOST + ":" + PORT + "/" +
                DB, USER, PASSWORD);


    }

    @Bean
    public BookDAOBean bookDAOBean() throws SQLException {
        return new BookDAOBean(newConnection());
    }
}
