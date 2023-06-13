package com.jpc16tuesday.springlibraryproject.library.service;

import com.jpc16tuesday.springlibraryproject.library.dto.GenericDTO;
import com.jpc16tuesday.springlibraryproject.library.exception.MyDeleteException;
import com.jpc16tuesday.springlibraryproject.library.mapper.GenericMapper;
import com.jpc16tuesday.springlibraryproject.library.model.GenericModel;
import com.jpc16tuesday.springlibraryproject.library.repository.GenericRepository;
import com.jpc16tuesday.springlibraryproject.library.service.GenericService;
import com.jpc16tuesday.springlibraryproject.library.service.userdetails.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class GenericTest<E extends GenericModel, D extends GenericDTO> {

    protected GenericService<E, D> service;
    protected GenericRepository<E> repository;
    protected GenericMapper<E, D> mapper;

    @BeforeEach
    void init() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                CustomUserDetails.builder()
                        .username("USER"),
                null,
                null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    protected abstract void getAll();
    protected abstract void getOne();
    protected abstract void create();
    protected abstract void update();
    protected abstract void delete() throws MyDeleteException;
    protected abstract void restore();
    protected abstract void getAllNotDeleted();

}
