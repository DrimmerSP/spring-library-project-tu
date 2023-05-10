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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"), foreignKey = @ForeignKey(name = "FK_BOOKS_AUTHORS"),
            inverseJoinColumns = @JoinColumn(name = "author_id"), inverseForeignKey = @ForeignKey(name = "FK_AUTHORS_BOOKS"))
    List<Author> authors;

    @OneToMany(mappedBy = "book")
    private List<BookRentInfo> bookRentInfos;

    /*
    @OneToMany(cascade=CascadeType.PERSIST, fetch = FetchType.LAZY)
    Book -> many Review (Книга имеет много отзывов/оценок) = отношение

    Book newBook = newBook("title");
    Review r1 = new Review("Good");
    Review r2 = new Review("Excellent");
    newBook.addReview(r1);
    newBook.addReview(r2);
    bookRepository.save(newBook)
    =>
    как результат данного кода, будет выполнено 3 запроса:
    insert into books()...;
    insert into reviews(r1)...;
    insert into reviews(r2)...;
    ----------------------------------------------------
    @OneToMany(cascade=CascadeType.MERGE)
    Book book = bookRepository.findById(1);
    book.setDescription("Updated Description")
    //book.getReviews().get(1);
    Review r1 = reviewRepository.findById(4);
    r1.setMark(3);
    book.save(book)
    =>
    update books set description = ? where id = book.id;
    update reviews set mark = ? where id = review.id;

    ----------------------------------------------------
    @OneToMany(cascade=CascadeType.REMOVE)
    Book book = bookRepository.findById(1);
    bookRepository.delete(book);
    =>

    delete from reviews where id = ?;
    delete from reviews where id = ?;
    delete from books where id = ?;

        ----------------------------------------------------
    @OneToMany(cascade=CascadeType.REMOVE, orphanRemoval=true)
    Book book = bookRepository.findById(1);
    book.removeReview(book.getReviews).get(0));
    =>

    delete from reviews where id = ?
     */

}
