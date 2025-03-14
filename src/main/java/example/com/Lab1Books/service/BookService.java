package example.com.Lab1Books.service;

import example.com.Lab1Books.BookCustomPage;
import example.com.Lab1Books.dto.BookDTO;
import example.com.Lab1Books.dto.CreateBookDTO;
import example.com.Lab1Books.dto.UpdateBookDTO;
import example.com.Lab1Books.entity.BookEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookDTO getBookById(@NotNull @Min(1) Long id);

    Optional<BookEntity> findByIsbn(@NotNull @NotBlank String isbn);

    List<BookDTO> getAllBooks();

    BookEntity createBook(@Valid @NotNull CreateBookDTO book);

    BookDTO updateBook(@Valid @NotNull UpdateBookDTO book, @NotNull @Min(1) Long id);

    Response deleteBook(@NotNull @Min(1) Long id);

    BookCustomPage<BookDTO> filterByAuthor(@NotBlank String author, @Min(1) int page, @Min(1) int size);

    BookCustomPage<BookDTO> filterByTitle(@NotBlank String title, @Min(1) int page, @Min(1) int size);

    BookCustomPage<BookDTO> filterByGenre(@NotBlank String genre, @Min(1) int page, @Min(1) int size);

    BookCustomPage<BookDTO> filterByAuthorAndTitle(@NotBlank String author, @NotBlank String title, @Min(1) int page, @Min(1) int size);

    BookCustomPage<BookDTO> filterByAuthorAndGenre(@NotBlank String author, @NotBlank String genre, @Min(1) int page, @Min(1) int size);

}


