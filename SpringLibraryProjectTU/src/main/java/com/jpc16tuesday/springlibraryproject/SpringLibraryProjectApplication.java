package com.jpc16tuesday.springlibraryproject;

import com.jpc16tuesday.springlibraryproject.dbexample.DBExampleStarter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;


@SpringBootApplication
public class SpringLibraryProjectApplication implements CommandLineRunner {

    DBExampleStarter dbExampleStarter;

    public SpringLibraryProjectApplication(DBExampleStarter dbExampleStarter) {
        this.dbExampleStarter = dbExampleStarter;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringLibraryProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws SQLException {
//        dbExampleStarter.runWeek1();
    }

}
