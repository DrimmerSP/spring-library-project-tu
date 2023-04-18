package com.jpc16tuesday.springlibraryproject.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_generator")
    private Long id;

    @Column(name = "title", nullable = false)
    private String bookTitle;

    @Column(name = "publish")
    private String publish;

    @Column(name = "publishDate", nullable = false)
    private LocalDate publishDate;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "storage_place", nullable = false)
    private String storagePlace;

    @Column(name = "online_copy_path")
    private String onlineCopyPath;

    @Column(name = "description")
    private String description;

    @Column(name = "genre", nullable = false)
    @Enumerated
    private Genre genre;

}
