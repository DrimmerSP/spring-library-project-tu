package com.jpc16tuesday.springlibraryproject.library.controllers.mvc;


import com.jpc16tuesday.springlibraryproject.library.dto.AddBookDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.AuthorDTO;
import com.jpc16tuesday.springlibraryproject.library.exception.MyDeleteException;
import com.jpc16tuesday.springlibraryproject.library.service.AuthorService;
import com.jpc16tuesday.springlibraryproject.library.service.BookService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.jpc16tuesday.springlibraryproject.library.constants.UserRolesConstants.*;
@Controller
@Hidden
@RequestMapping("/authors")
@Slf4j
public class MVCAuthorController {
    private final AuthorService authorService;
    private final BookService bookService;
    
    public MVCAuthorController(AuthorService authorService,
                               BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }
    
    @GetMapping("")
    public String getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "5") int pageSize,
                         Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "authorFIO"));
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<AuthorDTO> result;
        if (ADMIN.equalsIgnoreCase(userName)) {
            result = authorService.listAll(pageRequest);
        }
        else {
            result = authorService.listAllNotDeleted(pageRequest);
        }
        model.addAttribute("authors", result);
        return "authors/viewAllAuthors";
    }
    
    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id,
                         Model model) {
        model.addAttribute("author", authorService.getOne(id));
        return "authors/viewAuthor";
    }
    
    @GetMapping("/add")
    public String create() {
        return "authors/addAuthor";
    }
    
    @PostMapping("/add")
    public String create(@ModelAttribute("authorForm") AuthorDTO authorDTO) {
        authorService.create(authorDTO);
        return "redirect:/authors";
    }
    
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         Model model) {
        model.addAttribute("author", authorService.getOne(id));
        return "authors/updateAuthor";
    }
    
    @PostMapping("/update")
    public String update(@ModelAttribute("authorForm") AuthorDTO authorDTO) {
        authorService.update(authorDTO);
        return "redirect:/authors";
    }
    
    @GetMapping("/add-book/{authorId}")
    public String addBook(@PathVariable Long authorId,
                          Model model) {
        model.addAttribute("books", bookService.listAll());
        model.addAttribute("authorId", authorId);
        model.addAttribute("author", authorService.getOne(authorId).getAuthorFIO());
        return "authors/addAuthorBook";
    }
    
    @PostMapping("/add-book")
    public String addBook(@ModelAttribute("authorBookForm") AddBookDTO addBookDTO) {
        authorService.addBook(addBookDTO);
        return "redirect:/authors";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        authorService.deleteSoft(id);
        return "redirect:/authors";
    }
    
    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        authorService.restore(id);
        return "redirect:/authors";
    }
    
    @PostMapping("/search")
    public String searchAuthors(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "5") int pageSize,
                                @ModelAttribute("authorSearchForm") AuthorDTO authorDTO,
                                Model model) {
        if (StringUtils.hasText(authorDTO.getAuthorFIO()) || StringUtils.hasLength(authorDTO.getAuthorFIO())) {
            PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "authorFIO"));
            model.addAttribute("authors", authorService.searchAuthors(authorDTO.getAuthorFIO().trim(), pageRequest));
            return "authors/viewAllAuthors";
        }
        else {
            return "redirect:/authors";
        }
    }
}
