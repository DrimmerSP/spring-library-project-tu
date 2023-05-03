package com.jpc16tuesday.springlibraryproject.library.service;

import com.jpc16tuesday.springlibraryproject.library.dto.AuthorDTO;
import com.jpc16tuesday.springlibraryproject.library.mapper.AuthorMapper;
import com.jpc16tuesday.springlibraryproject.library.model.Author;
import com.jpc16tuesday.springlibraryproject.library.model.Book;
import com.jpc16tuesday.springlibraryproject.library.repository.AuthorRepository;
import com.jpc16tuesday.springlibraryproject.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class AuthorService
        extends GenericService<Author, AuthorDTO> {

    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository,
                         AuthorMapper authorMapper,
                         BookRepository bookRepository) {
        super(authorRepository, authorMapper);
        this.bookRepository = bookRepository;
    }

    public AuthorDTO addBook(Long bookId,
                             Long authorId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Книга не найдена!"));
        AuthorDTO author = getOne(authorId);
        author.getBooksIds().add(book.getId());
        update(author);
        return author;
    }
}
