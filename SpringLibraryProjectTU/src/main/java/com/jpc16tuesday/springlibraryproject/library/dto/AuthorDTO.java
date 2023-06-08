package com.jpc16tuesday.springlibraryproject.library.dto;

import com.jpc16tuesday.springlibraryproject.library.model.Author;
import com.jpc16tuesday.springlibraryproject.library.model.Book;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorDTO extends GenericDTO {
    private String authorFIO;
    private LocalDate birthDate;
    private String description;
    List<Long> booksIds;


    public AuthorDTO(Author author) {
        this.birthDate = author.getBirthDate();
        this.createdBy = author.getCreatedBy();
        this.authorFIO = author.getAuthorFIO();
        this.description = author.getDescription();
        this.createdWhen = author.getCreatedWhen();
        this.id = author.getId();
        List<Book> books = author.getBooks();
        List<Long> bookIds = new ArrayList<>();
        books.forEach(b -> bookIds.add(b.getId()));
        this.booksIds = bookIds;
        this.isDeleted = false;
    }
}
