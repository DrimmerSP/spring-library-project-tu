package com.jpc16tuesday.springlibraryproject.library.service;


import com.jpc16tuesday.springlibraryproject.library.dto.BookDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.BookRentInfoDTO;
import com.jpc16tuesday.springlibraryproject.library.mapper.BookRentInfoMapper;
import com.jpc16tuesday.springlibraryproject.library.model.BookRentInfo;
import com.jpc16tuesday.springlibraryproject.library.repository.BookRentInfoRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookRentInfoService
        extends GenericService<BookRentInfo, BookRentInfoDTO> {

    private final BookService bookService;

    protected BookRentInfoService(BookRentInfoRepository bookRentInfoRepository,
                                  BookRentInfoMapper bookRentInfoMapper, BookService bookService) {
        super(bookRentInfoRepository, bookRentInfoMapper);
        this.bookService = bookService;
    }

    public BookRentInfoDTO rentBook(final BookRentInfoDTO rentInfoDTO) {
        BookDTO bookDTO = bookService.getOne(rentInfoDTO.getBookId());
        bookDTO.setAmount(bookDTO.getAmount() - 1);
        bookService.update(bookDTO);

        long rentPeriod = rentInfoDTO.getRentPeriod() != null ? rentInfoDTO.getRentPeriod() : 14L;
        rentInfoDTO.setRentDate(LocalDateTime.now());
        rentInfoDTO.setReturned(false);
        rentInfoDTO.setRentPeriod((int) rentPeriod);
        rentInfoDTO.setReturnDate(LocalDateTime.now().plusDays(rentPeriod));
        rentInfoDTO.setCreatedWhen(LocalDateTime.now());
        rentInfoDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        return mapper.toDTO(repository.save(mapper.toEntity(rentInfoDTO)));
    }


}
