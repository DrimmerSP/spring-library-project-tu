package com.jpc16tuesday.springlibraryproject.library.repository;

import com.jpc16tuesday.springlibraryproject.library.model.Author;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository
        extends GenericRepository<Author> {
}
