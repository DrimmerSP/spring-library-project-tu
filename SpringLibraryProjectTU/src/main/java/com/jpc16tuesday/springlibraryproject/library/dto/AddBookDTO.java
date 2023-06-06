package com.jpc16tuesday.springlibraryproject.library.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddBookDTO {
    Long bookId;
    Long authorId;
}
