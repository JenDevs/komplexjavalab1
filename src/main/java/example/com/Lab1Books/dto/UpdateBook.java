package example.com.Lab1Books.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UpdateBook(
        String author,
        String title,
        String description,
        @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN must be 10 or 13 digits") String isbn,
        @NotBlank String genre,
        LocalDate releaseDate) {

}
