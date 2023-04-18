package com.jpc16tuesday.springlibraryproject.dbexample.model;


import lombok.*;

import java.util.Date;


//POJO - Plain Old Java Object
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    private String title;
    private String author;
    private Date dateAdded;
}
