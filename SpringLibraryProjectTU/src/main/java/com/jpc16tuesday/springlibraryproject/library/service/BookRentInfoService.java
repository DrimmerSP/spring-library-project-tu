package com.jpc16tuesday.springlibraryproject.library.service;


import com.jpc16tuesday.springlibraryproject.library.dto.BookDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.BookRentInfoDTO;
import com.jpc16tuesday.springlibraryproject.library.mapper.BookRentInfoMapper;
import com.jpc16tuesday.springlibraryproject.library.model.BookRentInfo;
import com.jpc16tuesday.springlibraryproject.library.repository.BookRentInfoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookRentInfoService
        extends GenericService<BookRentInfo, BookRentInfoDTO> {
    private final BookService bookService;

    protected BookRentInfoService(BookRentInfoRepository bookRentInfoRepository,
                                  BookRentInfoMapper bookRentInfoMapper,
                                  BookService bookService) {
        super(bookRentInfoRepository, bookRentInfoMapper);
        this.bookService = bookService;
    }

    public BookRentInfoDTO rentBook(final BookRentInfoDTO rentBookInfoDTO) {
        BookDTO bookDTO = bookService.getOne(rentBookInfoDTO.getBookId());
        bookDTO.setAmount(bookDTO.getAmount() - 1);
        bookService.update(bookDTO);
        long rentPeriod = rentBookInfoDTO.getRentPeriod() != null ? rentBookInfoDTO.getRentPeriod() : 14L;
        rentBookInfoDTO.setRentDate(LocalDateTime.now());
        rentBookInfoDTO.setReturned(false);
        rentBookInfoDTO.setRentPeriod((int) rentPeriod);
        rentBookInfoDTO.setReturnDate(LocalDateTime.now().plusDays(rentPeriod));
        rentBookInfoDTO.setCreatedWhen(LocalDateTime.now());
        rentBookInfoDTO.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return mapper.toDTO(repository.save(mapper.toEntity(rentBookInfoDTO)));
    }

    public Page<BookRentInfoDTO> listUserRentBooks(final Long id,
                                                   final Pageable pageRequest) {
        Page<BookRentInfo> objects = ((BookRentInfoRepository) repository).getBookRentInfoByUserId(id, pageRequest);
        List<BookRentInfoDTO> results = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(results, pageRequest, objects.getTotalElements());
    }

    public void returnBook(final Long id) {
        BookRentInfoDTO bookRentInfoDTO = getOne(id);
        bookRentInfoDTO.setReturned(true);
        bookRentInfoDTO.setReturnDate(LocalDateTime.now());
        BookDTO bookDTO = bookRentInfoDTO.getBookDTO();
        bookDTO.setAmount(bookDTO.getAmount() + 1);
        update(bookRentInfoDTO);
        bookService.update(bookDTO);
    }

}
