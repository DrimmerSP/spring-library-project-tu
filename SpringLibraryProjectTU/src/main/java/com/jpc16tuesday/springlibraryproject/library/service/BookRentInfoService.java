package com.jpc16tuesday.springlibraryproject.library.service;


import com.jpc16tuesday.springlibraryproject.library.dto.BookRentInfoDTO;
import com.jpc16tuesday.springlibraryproject.library.mapper.BookRentInfoMapper;
import com.jpc16tuesday.springlibraryproject.library.model.BookRentInfo;
import com.jpc16tuesday.springlibraryproject.library.repository.BookRentInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class BookRentInfoService
      extends GenericService<BookRentInfo, BookRentInfoDTO> {
    protected BookRentInfoService(BookRentInfoRepository bookRentInfoRepository,
                                  BookRentInfoMapper bookRentInfoMapper) {
        super(bookRentInfoRepository, bookRentInfoMapper);
    }
}
