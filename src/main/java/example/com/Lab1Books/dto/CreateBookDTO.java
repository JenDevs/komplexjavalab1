package example.com.Lab1Books.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public record CreateBookDTO(

        @NotBlank(message = "Author is required")
        @Size(min = 2, max = 255, message = "Author must be between 2 and 255 characters")
        String author,

        @NotBlank (message = "Title is required")
        @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
        String description,

        @NotBlank (message = "ISBN is required")
        @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN must be 10 or 13 digits")
        String isbn,

        @NotBlank(message = "Genre is required")
        @Size(min = 1, max = 50, message = "Genre must be between 1 and 50 characters")
        String genre,

        @NotNull (message = "Release date is required")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate releaseDate) {
}
