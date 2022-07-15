package com.example.library.controller;

import com.example.library.dto.AuthorRequestDto;
import com.example.library.dto.AuthorResponseDto;
import com.example.library.mapper.AuthorMapper;
import com.example.library.model.Author;
import com.example.library.service.AuthorService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponseDto createAuthor(@Valid @RequestBody AuthorRequestDto authorRequestDto) {
        return authorMapper.mapToDto(authorService.createAuthor(
                authorMapper.mapToModel(authorRequestDto)));
    }

    @GetMapping("/{id}")
    public AuthorResponseDto getById(@PathVariable Long id) {
        return authorMapper.mapToDto(authorService.getAuthorById(id));
    }

    @GetMapping
    public List<AuthorResponseDto> getAllAuthors() {
        return authorService.getAllAuthors()
                .stream()
                .map(authorMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public AuthorResponseDto updateAuthor(@PathVariable Long id,
                                      @Valid @RequestBody AuthorRequestDto authorRequestDto) {
        Author author = authorMapper.mapToModel(authorRequestDto);
        author.setId(id);
        return authorMapper.mapToDto(authorService.createAuthor(author));
    }

    @DeleteMapping("/{id}")
    public String deleteAuthorById(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
        return "Author by id " + id + " is deleted";
    }
}
