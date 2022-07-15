package com.example.library.controller;

import com.example.library.dto.BookRequestDto;
import com.example.library.dto.BookResponseDto;
import com.example.library.mapper.BookMapper;
import com.example.library.model.Book;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    public BookController(BookService bookService, BookMapper bookMapper,
                          AuthorService authorService) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.authorService = authorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto createBook(@Valid @RequestBody BookRequestDto bookRequestDto) {
        return bookMapper.mapToDto(bookService.createBook(bookMapper.mapToModel(bookRequestDto)));
    }

    @GetMapping("/{id}")
    public BookResponseDto getById(@PathVariable Long id) {
        return bookMapper.mapToDto(bookService.getBookById(id));
    }

    @GetMapping
    public List<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks().stream()
                .map(bookMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public BookResponseDto updateBook(@PathVariable Long id,
                                      @Valid @RequestBody BookRequestDto bookRequestDto) {
        Book book = bookMapper.mapToModel(bookRequestDto);
        book.setId(id);
        return bookMapper.mapToDto(bookService.createBook(book));
    }

    @DeleteMapping("/{id}")
    public String deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return "Book by id " + id + " was deleted";
    }

    @GetMapping("/author/{id}")
    public List<BookResponseDto> getAllBooksByAuthorId(@PathVariable Long id) {
        return bookService.getAllBooksByAuthorId(authorService.getAuthorById(id)).stream()
                .map(bookMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-author-name")
    public List<BookResponseDto> getAllBooksByAuthorName(@RequestParam String authorName) {
        return bookService.findAllByAuthorName(authorName).stream()
                .map(bookMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/max-sold-book-by-author-name")
    public BookResponseDto getBookWithMaxSoldAmount(@RequestParam String authorName) {
        Book book = bookService.getBookByMaxSoldAmountByAuthorName(authorName);
        return bookMapper.mapToDto(book);
    }

    @GetMapping("/max-published-book-by-author-name")
    public BookResponseDto getBookWithMaxPublishedAmount(@RequestParam String authorName) {
        Book book = bookService.getBookPublishedAmountByAuthorName(authorName);
        return bookMapper.mapToDto(book);
    }

    @GetMapping("/most-success-rate-by-part-name")
    public List<BookResponseDto> getAllBooksWithSuccessRateOfAuthor(
            @RequestParam String authorName) {
        return bookService.getAllBooksSuccessRateOfAuthor(authorName).stream()
                .map(bookMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
