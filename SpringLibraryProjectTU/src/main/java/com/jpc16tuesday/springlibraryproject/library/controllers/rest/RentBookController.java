package com.jpc16tuesday.springlibraryproject.library.controllers.rest;


import com.jpc16tuesday.springlibraryproject.library.dto.BookRentInfoDTO;
import com.jpc16tuesday.springlibraryproject.library.model.BookRentInfo;
import com.jpc16tuesday.springlibraryproject.library.service.BookRentInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest/rent/info")
@Tag(name = "Аренда книг",
     description = "Контроллер для работы с арендой/выдачей книг пользователям библиотеки")
public class RentBookController
      extends GenericController<BookRentInfo, BookRentInfoDTO> {
    
    public RentBookController(BookRentInfoService bookRentInfoService) {
        super(bookRentInfoService);
    }
}
