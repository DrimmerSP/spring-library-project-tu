package com.jpc16tuesday.springlibraryproject.library.repository;

import com.jpc16tuesday.springlibraryproject.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository
        extends GenericRepository<Book> {

    @Query(nativeQuery = true,
            value = """
                       select distinct b.*
                       from books b
                                left join books_authors ba on b.id = ba.book_id
                                left join authors a on a.id = ba.author_id
                       where b.title ilike '%' || coalesce(:title, '%')  || '%'
                         and cast(b.genre as char) like coalesce(:genre, '%')
                         and coalesce(a.fio, '%') ilike '%' ||  coalesce(:fio, '%')  || '%'
                         and b.is_deleted = false
                    """)
    Page<Book> searchBooks(@Param(value = "title") String bookTitle,
                           @Param(value = "genre") String genre,
                           @Param(value = "fio") String authorFio,
                           Pageable pageRequest);
}
