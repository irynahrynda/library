package com.example.library.repository;

import com.example.library.model.Author;
import com.example.library.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAuthor(Author author);

    List<Book> findAllByAuthor_AuthorName(String authorName);

    @Query(value = "select b "
            + "from Book b "
            + "where b.author.authorName = :authorName and "
            + "b.soldAmount = (select max(b.soldAmount) from Book b "
            + "where b.author.authorName = :authorName)")
    Book getBookByMaxSoldAmount(String authorName);

    @Query(value = "select b "
            + "from Book b "
            + "where b.author.authorName = :authorName and "
            + "b.publishedAmount = (select max(b.publishedAmount) from Book b "
            + "where b.author.authorName = :authorName)")
    Book getBookPublishedAmount(String authorName);

    @Query(value = "select b "
            + "from Book b "
            + "where b.author.authorName = :authorName and "
            + "b.soldAmount / b.publishedAmount = (select max(b.soldAmount / b.publishedAmount) "
            + "from Book b where b.author.authorName = :authorName)")
    List<Book> getAllBooksSuccessRateOfAuthor(String authorName);
}
