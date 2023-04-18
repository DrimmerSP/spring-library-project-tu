package com.jpc16tuesday.springlibraryproject.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_rent_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "book_rent_info_seq", allocationSize = 1)
public class BookRentInfo extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RENT_BOOK_INFO_USER"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_RENT_BOOK_INFO_BOOK"))
    private Book book;

    @Column(name = "rent_date", nullable = false)
    private LocalDateTime rentDate;

    //поле автоматически должно рассчитываться из rent_date + rent_perion
    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    //Количество дней аренды, по умолчанию 14
    @Column(name = "rent_period", nullable = false)
    private Integer rentPeriod;

    @Column(name = "returned", nullable = false)
    private Boolean returned;

}
