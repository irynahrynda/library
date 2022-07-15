package com.example.library.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequestDto {
    @NotNull
    private String bookName;
    @NotNull
    private int publishedAmount;
    @NotNull
    private int soldAmount;
    @NotNull
    private Long authorId;
}
