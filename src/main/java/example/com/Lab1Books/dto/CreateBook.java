package example.com.Lab1Books.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// @ValidBook(message = "Not the default message")
public record CreateBook(
        @NotBlank String author,
        @NotBlank String title,
        @NotBlank String description,
        @NotBlank @NotNull String isbn,
        @NotBlank String genre,
        @NotNull LocalDate releaseDate) {


}
