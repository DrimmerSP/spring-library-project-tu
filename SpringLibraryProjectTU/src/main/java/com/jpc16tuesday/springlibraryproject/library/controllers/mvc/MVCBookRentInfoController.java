package com.jpc16tuesday.springlibraryproject.library.controllers.mvc;

import com.jpc16tuesday.springlibraryproject.library.dto.BookRentInfoDTO;
import com.jpc16tuesday.springlibraryproject.library.service.BookRentInfoService;
import com.jpc16tuesday.springlibraryproject.library.service.BookService;
import com.jpc16tuesday.springlibraryproject.library.service.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/rent")
public class MVCBookRentInfoController {

    private final BookRentInfoService bookRentInfoService;
    private final BookService bookService;

    public MVCBookRentInfoController(BookRentInfoService bookRentInfoService,
                                     BookService bookService) {
        this.bookRentInfoService = bookRentInfoService;
        this.bookService = bookService;
    }


    @GetMapping("/book/{bookId}")
    public String rentBook(@PathVariable Long bookId,
                           Model model) {
        model.addAttribute("book", bookService.getOne(bookId));
        return "userBooks/rentBook";
    }

    @PostMapping("/book")
    public String rentBook(@ModelAttribute("rentBookInfo")BookRentInfoDTO bookRentInfoDTO) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookRentInfoDTO.setUserId(Long.valueOf(customUserDetails.getUserId()));
        bookRentInfoService.rentBook(bookRentInfoDTO);
        return "redirect:/rent/user-books/" + customUserDetails.getUserId();
    }

    @GetMapping("/user-books/{id}")
    public String userBooks(@RequestParam(value = "page", defaultValue = "1") int page,
                            @RequestParam(value = "size", defaultValue = "5") int pageSize,
                            @PathVariable Long id,
                            Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<BookRentInfoDTO> rentInfoDTOPage = bookRentInfoService.listAll(pageRequest);
        model.addAttribute("rentBooks", rentInfoDTOPage);
        return "userBooks/viewAllUserBooks";
    }


}
