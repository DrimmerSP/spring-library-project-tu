package com.jpc16tuesday.springlibraryproject.library.dto;

import com.jpc16tuesday.springlibraryproject.library.model.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class BookDTO
      extends GenericDTO {
    private String bookTitle;
    private LocalDate publishDate;
    private String publish;
    private Integer pageCount;
    private Integer amount;
    private String storagePlace;
    private String onlineCopyPath;
    private String description;
    private Genre genre;
    private List<Long> authorIds;
    private List<AuthorDTO> authorInfo;
}
