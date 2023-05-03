package com.jpc16tuesday.springlibraryproject.library.service;


import com.jpc16tuesday.springlibraryproject.library.dto.BookDTO;
import com.jpc16tuesday.springlibraryproject.library.mapper.BookMapper;
import com.jpc16tuesday.springlibraryproject.library.model.Author;
import com.jpc16tuesday.springlibraryproject.library.model.Book;
import com.jpc16tuesday.springlibraryproject.library.repository.AuthorRepository;
import com.jpc16tuesday.springlibraryproject.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class BookService
      extends GenericService<Book, BookDTO> {
    private final AuthorRepository authorRepository;
    
    protected BookService(BookRepository repository,
                          BookMapper mapper,
                          AuthorRepository authorRepository) {
        super(repository, mapper);
        this.authorRepository = authorRepository;
    }
    
    public BookDTO addAuthor(final Long bookId,
                             final Long authorId) {
        BookDTO book = getOne(bookId);
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException("автор не найден"));
        book.getAuthorIds().add(author.getId());
        update(book);
        return book;
    }
}
