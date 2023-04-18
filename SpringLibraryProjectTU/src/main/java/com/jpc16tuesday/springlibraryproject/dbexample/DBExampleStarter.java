package com.jpc16tuesday.springlibraryproject.dbexample;

import com.jpc16tuesday.springlibraryproject.dbexample.dao.BookDAOBean;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class DBExampleStarter {
    private final BookDAOBean bookDAOBean;

    //    Инжект бина через конструктор
    public DBExampleStarter(BookDAOBean bookDAOBean) {
        this.bookDAOBean = bookDAOBean;
    }

    //    Инжект бина через сеттер (сеттер может иметь разное название)
//    @Autowired
//    public void setSomething(BookDAOBean bookDAOBean) {
//        this.bookDAOBean = bookDAOBean;
//    }
//    JDBC обертка от спринга (зависимость spring-boot-starter-jdbc)
//    @Autowired
//    private NamedParameterJdbcTemplate jdbcTemplate;


    public void runWeek1() throws SQLException {
        //Шаг 1 (Большая связность кода)
//        BookDaoJDBC bookDaoJDBC = new BookDaoJDBC();
//        bookDaoJDBC.findBookById(3);

        //Шаг 2 (Сделали BookDaoBean - добавили поле connection)
//        BookDAOBean bookDAOBean = new BookDAOBean(DBConnection.INSTANCE.newConnection());
//        bookDAOBean.findBookById(2);

        //Шаг 3 - Работаем с Spring Context
//        ApplicationContext ctx = new AnnotationConfigApplicationContext(MyDBConfigContext.class);
//        BookDAOBean bookDAOBean = ctx.getBean(BookDAOBean.class);
//        bookDAOBean.findBookById(3);

        //Шаг 4 - для MyDBConfigContext добавляем аннотацию @ComponentScan,удаляем связь на BookDAOBean
        //Добавляем аннотацию @Component для BookDAOBean (на 3 шаги был без аннотации)

        //Финальный шаг
        bookDAOBean.findBookById(3);

        //JDBC обертка от спринга (зависимость spring-boot-starter-jdbc) -> работа с темплейтом
//        List<Book> bookList = jdbcTemplate.query("select * from books",
//                ((rs, rowNum) -> new Book(
//                        rs.getInt("id"),
//                        rs.getString("title"),
//                        rs.getString("author"),
//                        rs.getDate("date_added")
//                )));
//        bookList.forEach(System.out::println);
    }

}
