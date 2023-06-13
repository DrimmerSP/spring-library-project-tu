package com.jpc16tuesday.springlibraryproject.library.service;

import com.jpc16tuesday.springlibraryproject.library.constants.Errors;
import com.jpc16tuesday.springlibraryproject.library.dto.AddBookDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.AuthorDTO;
import com.jpc16tuesday.springlibraryproject.library.exception.MyDeleteException;
import com.jpc16tuesday.springlibraryproject.library.mapper.AuthorMapper;
import com.jpc16tuesday.springlibraryproject.library.model.Author;
import com.jpc16tuesday.springlibraryproject.library.model.Book;
import com.jpc16tuesday.springlibraryproject.library.repository.AuthorRepository;
import com.jpc16tuesday.springlibraryproject.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class AuthorService
        extends GenericService<Author, AuthorDTO> {

    public AuthorService(AuthorRepository authorRepository,
                         AuthorMapper authorMapper) {
        super(authorRepository, authorMapper);
    }

    public AuthorDTO addBook(final AddBookDTO addBookDTO) {
        AuthorDTO author = getOne(addBookDTO.getAuthorId());
        author.getBooksIds().add(addBookDTO.getBookId());
        update(author);
        return author;
    }

    public Page<AuthorDTO> searchAuthors(final String fio,
                                         Pageable pageable) {
        Page<Author> authors = ((AuthorRepository)repository).findAllByAuthorFIOContainsIgnoreCaseAndIsDeletedFalse(fio, pageable);
        List<AuthorDTO> result = mapper.toDTOs(authors.getContent());
        return new PageImpl<>(result, pageable, authors.getTotalElements());
    }

    @Override
    public void deleteSoft(Long objectId) throws MyDeleteException {
        Author author = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Автора с заданным id=" + objectId + " не существует."));
        boolean authorCanBeDeleted = ((AuthorRepository)repository).checkAuthorForDeletion(objectId);
        if (authorCanBeDeleted) {
            markAsDeleted(author);
            List<Book> books = author.getBooks();
            if (books != null && books.size() > 0) {
                books.forEach(this::markAsDeleted);
            }
            ((AuthorRepository)repository).save(author);
        }
        else {
            throw new MyDeleteException(Errors.Authors.AUTHOR_DELETE_ERROR);
        }
    }

    public void restore(Long objectId) {
        Author author = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Автора с заданным id=" + objectId + " не существует."));
        unMarkAsDeleted(author);
        List<Book> books = author.getBooks();
        if (books != null && books.size() > 0) {
            books.forEach(this::unMarkAsDeleted);
        }
        repository.save(author);
    }
}
