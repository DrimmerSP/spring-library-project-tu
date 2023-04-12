package com.jpc16tuesday.springlibraryproject;

import com.jpc16tuesday.springlibraryproject.dbexample.MyDBConfigContext;
import com.jpc16tuesday.springlibraryproject.dbexample.dao.BookDAOBean;
import com.jpc16tuesday.springlibraryproject.dbexample.dao.BookDaoJDBC;
import com.jpc16tuesday.springlibraryproject.dbexample.db.DBConnection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class SpringLibraryProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringLibraryProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Вариант 1 (Большая связность кода)
//        BookDaoJDBC bookDaoJDBC = new BookDaoJDBC();
//        bookDaoJDBC.findBookById(3);

        //Вариант 2 (Сделали BookDaoBean - добавили поле connection)
//        BookDAOBean bookDAOBean = new BookDAOBean(DBConnection.INSTANCE.newConnection());
//        bookDAOBean.findBookById(2);

        //Вариант 3 - Работаем с Spring Context
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MyDBConfigContext.class);
        BookDAOBean bookDAOBean = ctx.getBean(BookDAOBean.class);
        bookDAOBean.findBookById(3);

    }
}
