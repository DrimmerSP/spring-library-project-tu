package com.jpc16tuesday.springlibraryproject.library.controllers.mvc;

import com.jpc16tuesday.springlibraryproject.library.dto.BookDTO;
import com.jpc16tuesday.springlibraryproject.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/books")
public class MVCBookController {

    private final BookService bookService;

    public MVCBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getAll(Model model) {
        List<BookDTO> books = bookService.listAll();
        model.addAttribute("books", books);
        return "books/viewAllBooks";
    }

    @GetMapping("/add")
    public String create() {
        return "books/addBook";
    }
    @PostMapping("/add")
    public String create(@ModelAttribute("bookForm") BookDTO newBook) {
        log.info(newBook.toString());
        bookService.create(newBook);
        return "redirect:/books";

    }

}
