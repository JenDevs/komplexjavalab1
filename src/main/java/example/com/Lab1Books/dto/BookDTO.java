package example.com.Lab1Books.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO for {@link example.com.Lab1Books.entity.BookEntity}
 */

public record BookDTO(
        Long id,

        String author,

        String title,

        String description,

        String isbn,

        String genre,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate releaseDate) {

}
