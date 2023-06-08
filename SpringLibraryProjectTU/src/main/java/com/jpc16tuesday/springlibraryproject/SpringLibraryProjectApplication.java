package com.jpc16tuesday.springlibraryproject;

import com.jpc16tuesday.springlibraryproject.dbexample.DBExampleStarter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@EnableScheduling
public class SpringLibraryProjectApplication implements CommandLineRunner {

    @Value("${server.port}")
    private String serverPort;

    DBExampleStarter dbExampleStarter;

    public SpringLibraryProjectApplication(DBExampleStarter dbExampleStarter) {
        this.dbExampleStarter = dbExampleStarter;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringLibraryProjectApplication.class, args);
    }

    @Override
    public void run(String... args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("admin");
        System.out.println(hashedPassword);

        System.out.println("Swagger path: http://localhost:" + serverPort + "/swagger-ui/index.html");
        System.out.println("Application path: http://localhost:" + serverPort);
    }

}
