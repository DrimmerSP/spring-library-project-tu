package com.jpc16tuesday.springlibraryproject.library.repository;

import com.jpc16tuesday.springlibraryproject.library.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository
        extends GenericRepository<Book> {
}
