package example.com.Lab1Books.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateBook(
        @NotNull Long id,
        @NotBlank String author,
        @NotBlank String title,
        @NotBlank String description,
        @NotBlank @NotNull String isbn,
        @NotBlank @NotNull LocalDate releaseDate) {
}
