package com.jpc16tuesday.springlibraryproject.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class AuthorDTO extends GenericDTO {
    private String authorFIO;
    private LocalDate birthDate;
    private String description;
    List<Long> booksIds;


//    public AuthorDTO(Author author) {
//        this.birthDate = author.getBirthDate();
//        this.createdBy = author.getCreatedBy();
//        this.authorFIO = author.getAuthorFIO();
//        this.description = author.getDescription();
//        this.id = author.getId();
//        List<Book> books = author.getBooks();
//        List<Long> bookIds = new ArrayList<>();
//        books.forEach(book -> bookIds.add(book.getId()));
//        this.bookIds = bookIds;
//    }
//
//    public static List<AuthorDTO> getAuthorDTOs(List<Author> authors) {
//        List<AuthorDTO> authorDTOS = new ArrayList<>();
//        for (Author author : authors) {
//            authorDTOS.add(new AuthorDTO(author));
//        }
//        return authorDTOS;
//    }
}
