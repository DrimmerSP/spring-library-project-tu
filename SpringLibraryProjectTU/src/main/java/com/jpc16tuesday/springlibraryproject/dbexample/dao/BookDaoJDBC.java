package com.jpc16tuesday.springlibraryproject.dbexample.dao;

import com.jpc16tuesday.springlibraryproject.dbexample.db.DBConnection;
import com.jpc16tuesday.springlibraryproject.dbexample.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class BookDaoJDBC {
    public Book findBookById(Integer bookId) {
        try (Connection connection = DBConnection.INSTANCE.newConnection()) {

            if (connection != null) {
                System.out.println("Ура! Мы подключились к БД!!!");
            } else {
                System.out.println("БД недоступна...");
            }
            String select = "select * from books where id = ?";
            assert connection != null;
            PreparedStatement selectQuery = connection.prepareStatement(select);
            selectQuery.setInt(1, bookId);
            ResultSet resultSet = selectQuery.executeQuery();

            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setAuthor(resultSet.getString("author"));
                book.setTitle(resultSet.getString("title"));
                book.setDateAdded(resultSet.getDate("date_added"));
//                books.add(book);
                System.out.println(book);
                return book;
            }



        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }
}
