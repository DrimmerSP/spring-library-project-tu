package com.jpc16tuesday.springlibraryproject.library.controllers.mvc;

import com.jpc16tuesday.springlibraryproject.library.dto.AuthorDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.BookDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.BookSearchDTO;
import com.jpc16tuesday.springlibraryproject.library.exception.MyDeleteException;
import com.jpc16tuesday.springlibraryproject.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/books")
public class MVCBookController {

    private final BookService bookService;

    public MVCBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int pageSize,
                         Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "bookTitle"));
        Page<BookDTO> books = bookService.getAllBooks(pageRequest);
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

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id,
                         Model model) {
        model.addAttribute("book", bookService.getOne(id));
        return "books/viewBook";
    }


    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         Model model) {
        model.addAttribute("book", bookService.getOne(id));
        return "books/updateBook";
    }

    @PostMapping
    public String update(@ModelAttribute("bookForm") BookDTO bookDTO) {
        bookService.update(bookDTO);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        bookService.deleteSoft(id);
        return "redirect:/books";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        bookService.restore(id);
        return "redirect:/books";
    }


    @PostMapping("/search")
    public String searchBooks(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int pageSize,
                              @ModelAttribute("bookSearchForm") BookSearchDTO bookSearchDTO,
                              Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "title"));
        model.addAttribute("books", bookService.searchBook(bookSearchDTO, pageRequest));
        return "books/viewAllBooks";
    }


    /**
     * Метод для поиска книги по ФИО автора (редирект по кнопке "Посмотреть книги" на странице автора)
     *
     * @param page      - текущая страница
     * @param pageSize  - количество объектов на странице
     * @param authorDTO - ДТО автора
     * @param model     - модель
     * @return - форму со списком всех книг подходящих под критерии (по фио автора)
     */
    @PostMapping("/search/booksByAuthor")
    public String searchBooks(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int pageSize,
                              @ModelAttribute("authorSearchForm") AuthorDTO authorDTO,
                              Model model) {
        BookSearchDTO bookSearchDTO = new BookSearchDTO();
        bookSearchDTO.setAuthorFIO(authorDTO.getAuthorFIO());
        return searchBooks(page, pageSize, bookSearchDTO,model);
    }

}
