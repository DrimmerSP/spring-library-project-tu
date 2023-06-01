package com.jpc16tuesday.springlibraryproject.library.mapper;

import com.jpc16tuesday.springlibraryproject.library.dto.BookRentInfoDTO;
import com.jpc16tuesday.springlibraryproject.library.model.BookRentInfo;
import com.jpc16tuesday.springlibraryproject.library.repository.BookRepository;
import com.jpc16tuesday.springlibraryproject.library.repository.UserRepository;
import com.jpc16tuesday.springlibraryproject.library.service.BookService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.util.List;


@Component
public class BookRentInfoMapper
        extends GenericMapper<BookRentInfo, BookRentInfoDTO> {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private final BookService bookService;

    protected BookRentInfoMapper(ModelMapper mapper,
                                 BookRepository bookRepository,
                                 UserRepository userRepository,
                                 BookService bookService) {
        super(BookRentInfo.class, BookRentInfoDTO.class, mapper);
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
    }

    @PostConstruct
    public void setupMapper() {
        super.modelMapper.createTypeMap(BookRentInfo.class, BookRentInfoDTO.class)
                .addMappings(m -> m.skip(BookRentInfoDTO::setUserId))
                .addMappings(m -> m.skip(BookRentInfoDTO::setBookId))
                .addMappings(m -> m.skip(BookRentInfoDTO::setBookDTO))
                .setPostConverter(toDTOConverter());

        super.modelMapper.createTypeMap(BookRentInfoDTO.class, BookRentInfo.class)
                .addMappings(m -> m.skip(BookRentInfo::setUser))
                .addMappings(m -> m.skip(BookRentInfo::setBook))
                .setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(BookRentInfoDTO source, BookRentInfo destination) {
        destination.setBook(bookRepository.findById(source.getBookId()).orElseThrow(() -> new NotFoundException("Книги не найдено")));
        destination.setUser(userRepository.findById(source.getUserId()).orElseThrow(() -> new NotFoundException("Пользователя не найдено")));
    }

    @Override
    protected void mapSpecificFields(BookRentInfo source, BookRentInfoDTO destination) {
        destination.setUserId(source.getUser().getId());
        destination.setBookId(source.getBook().getId());
        destination.setBookDTO(bookService.getOne(source.getBook().getId()));
    }

    @Override
    protected List<Long> getIds(BookRentInfo entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}
