package com.jpc16tuesday.springlibraryproject.library.service;

import com.jpc16tuesday.springlibraryproject.library.dto.AddBookDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.AuthorDTO;
import com.jpc16tuesday.springlibraryproject.library.exception.MyDeleteException;
import com.jpc16tuesday.springlibraryproject.library.mapper.AuthorMapper;
import com.jpc16tuesday.springlibraryproject.library.model.Author;
import com.jpc16tuesday.springlibraryproject.library.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorServiceTest
        extends GenericTest<Author, AuthorDTO> {

    public AuthorServiceTest() {
        super();
        repository = Mockito.mock(AuthorRepository.class);
        mapper = Mockito.mock(AuthorMapper.class);
        service = new AuthorService((AuthorRepository) repository, (AuthorMapper) mapper);

        AuthorTestData.AUTHOR_1.setDeleted(false);
        AuthorTestData.AUTHOR_2.setDeleted(false);
        AuthorTestData.AUTHOR_3.setDeleted(false);
    }

    @Test
    @Order(1)
    @Override
    protected void getAll() {
        Mockito.when(repository.findAll()).thenReturn(AuthorTestData.AUTHOR_LIST);
        Mockito.when(mapper.toDTOs(AuthorTestData.AUTHOR_LIST)).thenReturn(AuthorTestData.AUTHOR_DTO_LIST);

        List<AuthorDTO> authorDTOS = service.listAll();
        log.info("Testing getAll(): {}", authorDTOS);
        assertEquals(AuthorTestData.AUTHOR_LIST.size(), authorDTOS.size());
    }

    @Order(2)
    @Test
    @Override
    protected void getOne() { //TODO://Negative test to do
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        Mockito.when(mapper.toDTO(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);

        AuthorDTO authorDTO = service.getOne(1L);
        log.info("Testing getOne(): {}", authorDTO);
        assertEquals(AuthorTestData.AUTHOR_DTO_1, authorDTO);
    }

    @Order(3)
    @Test
    @Override
    protected void create() {
        Mockito.when(mapper.toEntity(AuthorTestData.AUTHOR_DTO_1)).thenReturn(AuthorTestData.AUTHOR_1);
        Mockito.when(mapper.toDTO(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);

        AuthorDTO authorDTO = service.create(AuthorTestData.AUTHOR_DTO_1);
        log.info("Testing create(): {}", authorDTO);
    }

    @Order(4)
    @Test
    @Override
    protected void update() {
        Mockito.when(mapper.toEntity(AuthorTestData.AUTHOR_DTO_1)).thenReturn(AuthorTestData.AUTHOR_1);
        Mockito.when(mapper.toDTO(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);

        AuthorDTO authorDTO = service.update(AuthorTestData.AUTHOR_DTO_1);
        log.info("Testing create(): {}", authorDTO);
    }

    @Order(5)
    @Test
    @Override
    protected void delete() throws MyDeleteException {
        Mockito.when(((AuthorRepository) repository).checkAuthorForDeletion(1L)).thenReturn(true);
        Mockito.when(((AuthorRepository) repository).checkAuthorForDeletion(2L)).thenReturn(false);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));

        service.deleteSoft(1L);
        assertTrue(AuthorTestData.AUTHOR_1.isDeleted());
    }

    @Test
    @Override
    protected void restore() {
        AuthorTestData.AUTHOR_3.setDeleted(true);
        Mockito.when(repository.save(AuthorTestData.AUTHOR_3)).thenReturn(AuthorTestData.AUTHOR_3);
        Mockito.when(repository.findById(3L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_3));
        log.info("Tested restore() before: " + AuthorTestData.AUTHOR_3.isDeleted());
        service.restore(3L);
        log.info("Tested restore() after: " + AuthorTestData.AUTHOR_3.isDeleted());
        assertFalse(AuthorTestData.AUTHOR_3.isDeleted());
    }

    @Test
    @Override
    protected void getAllNotDeleted() {
        List<Author> authors = AuthorTestData.AUTHOR_LIST.stream().filter(Predicate.not(Author::isDeleted)).toList();
        Mockito.when(repository.findAllByIsDeletedFalse()).thenReturn(authors);
        Mockito.when(mapper.toDTOs(authors))
                .thenReturn(AuthorTestData.AUTHOR_DTO_LIST.stream().filter(Predicate.not(AuthorDTO::isDeleted)).toList());
        List<AuthorDTO> authorDTOS = service.listAllNotDeleted();
        log.info("Testing getAllNotDeleted(): {}", authorDTOS);
        assertEquals(authors.size(), authorDTOS.size());
    }

    @Test
    void searchAuthors() {
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "authorFio"));

        Mockito.when(((AuthorRepository) repository).findAllByAuthorFIOContainsIgnoreCaseAndIsDeletedFalse("authorFio1", pageRequest))
                .thenReturn(new PageImpl<>(AuthorTestData.AUTHOR_LIST));
        Mockito.when(mapper.toDTOs(AuthorTestData.AUTHOR_LIST)).thenReturn(AuthorTestData.AUTHOR_DTO_LIST);

        Page<AuthorDTO> authorDTOList = ((AuthorService) service).searchAuthors("authorFio1", pageRequest);
        assertEquals(AuthorTestData.AUTHOR_DTO_LIST, authorDTOList.getContent());
    }

    @Test
    void addBook() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        Mockito.when(service.getOne(1L)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);

        ((AuthorService) service).addBook(new AddBookDTO(1L, 1L));
        log.info("Testing addBook(): {}", AuthorTestData.AUTHOR_DTO_1.getBooksIds());
        assertTrue(AuthorTestData.AUTHOR_DTO_1.getBooksIds().size() >= 1);
    }
}
