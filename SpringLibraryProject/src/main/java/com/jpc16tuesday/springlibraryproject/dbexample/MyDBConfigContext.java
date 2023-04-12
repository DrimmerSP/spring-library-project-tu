package com.jpc16tuesday.springlibraryproject.dbexample;

import com.jpc16tuesday.springlibraryproject.dbexample.dao.BookDAOBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import static com.jpc16tuesday.springlibraryproject.dbexample.constants.DBConstants.*;

//@Configuration
//@ComponentScan
//@ComponentScan(value = {"com.jpc16tuesday.springlibraryproject.dbexample.dao"})
public class MyDBConfigContext {
    private final Environment env;

    public MyDBConfigContext(Environment env) {
        this.env = env;
    }

    @Bean
    @Scope("singleton")
    public Connection newConnection() throws SQLException {
        return DriverManager.getConnection(
                Objects.requireNonNull(env.getProperty("spring.datasource.url")),
                env.getProperty("spring.datasource.username"),
                env.getProperty("spring.datasource.password")
        );
    }

//    @Bean
//    public BookDAOBean bookDAOBean() throws SQLException {
//        return new BookDAOBean(newConnection());
//    }
}
