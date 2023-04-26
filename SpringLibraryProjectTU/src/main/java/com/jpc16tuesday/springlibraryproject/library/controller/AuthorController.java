package com.jpc16tuesday.springlibraryproject.library.controller;

import com.jpc16tuesday.springlibraryproject.library.model.Author;
import com.jpc16tuesday.springlibraryproject.library.model.Book;
import com.jpc16tuesday.springlibraryproject.library.repository.AuthorRepository;
import com.jpc16tuesday.springlibraryproject.library.repository.BookRepository;
import com.jpc16tuesday.springlibraryproject.library.repository.GenericRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;


//@Hidden
@RestController
@RequestMapping("/authors") // http://localhost:8080/authors
@Tag(name = "Авторы", description = "Контроллер для работы с авторами книг из библиотеки")
public class AuthorController
        extends GenericController<Author> {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public AuthorController(GenericRepository<Author> genericRepository, BookRepository bookRepository, AuthorRepository authorRepository) {
        super(genericRepository);
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Operation(description = "Добавить книгу к автору")
    @RequestMapping(value = "/addBook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> addBook(@RequestParam(value = "bookId") Long bookId,
                                          @RequestParam(value = "authorId") Long authorId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("книга не найдена"));
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException("автор не найден"));
//        author.setBooks(List.of(book)); - перетрем
        author.getBooks().add(book);
        return ResponseEntity.status(HttpStatus.OK).body(authorRepository.save(author));
    }


}