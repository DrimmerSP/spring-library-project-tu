package com.jpc16tuesday.springlibraryproject.library.dto;

import com.jpc16tuesday.springlibraryproject.library.model.Genre;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookSearchDTO {
    private String bookTitle;
    private String authorFIO;
    private Genre genre;
}
