package com.jpc16tuesday.springlibraryproject.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "authors_sequence", allocationSize = 1)
public class Author extends GenericModel {

    @Column(name = "fio", nullable = false)
    private String authorFIO;

    @Column(name = "bith_date")
    private LocalDate birthDate;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "author_id"), foreignKey = @ForeignKey(name = "FK_AUTHORS_BOOKS"),
            inverseJoinColumns = @JoinColumn(name = "book_id"), inverseForeignKey = @ForeignKey(name = "FK_BOOKS_AUTHORS"))
    List<Book> books;
}
