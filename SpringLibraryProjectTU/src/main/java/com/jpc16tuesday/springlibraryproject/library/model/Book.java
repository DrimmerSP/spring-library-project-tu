package com.jpc16tuesday.springlibraryproject.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "books_sequence", allocationSize = 1)
public class Book extends GenericModel {

    @Column(name = "title", nullable = false)
    private String bookTitle;

    @Column(name = "publish")
    private String publish;

    @Column(name = "publish_date", nullable = false)
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

    @ManyToMany(mappedBy = "books")
    List<Author> authors;

}
