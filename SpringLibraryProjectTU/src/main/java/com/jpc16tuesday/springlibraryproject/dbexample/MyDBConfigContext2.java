package com.jpc16tuesday.springlibraryproject.dbexample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class MyDBConfigContext2 {
    private final AppConfiguration env;

    public MyDBConfigContext2(AppConfiguration env) {
        this.env = env;
    }

    @Bean
    @Scope("singleton")
    public Connection newConnection() throws SQLException {
        return DriverManager.getConnection(env.DB_URL, env.USER, env.PASSWORD);
    }
}
