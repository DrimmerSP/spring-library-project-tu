package com.jpc16tuesday.springlibraryproject.dbexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Value("${spring.datasource.url}")
    public String DB_URL;
    @Value("${spring.datasource.username}")
    public String USER;
    @Value("${spring.datasource.password}")
    public String PASSWORD;

}

