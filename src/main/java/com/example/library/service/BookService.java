package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import java.util.List;

public interface BookService {
    Book createBook(Book book);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    void deleteBookById(Long id);

    List<Book> getAllBooksByAuthorId(Author author);

    List<Book> findAllByAuthorName(String authorName);

    Book getBookByMaxSoldAmountByAuthorName(String authorName);

    Book getBookPublishedAmountByAuthorName(String authorName);

    List<Book> getAllBooksSuccessRateOfAuthor(String authorName);
}
