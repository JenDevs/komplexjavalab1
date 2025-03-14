package example.com.Lab1Books.mapper;

import example.com.Lab1Books.BookCustomPage;
import example.com.Lab1Books.dto.BookDTO;
import example.com.Lab1Books.dto.CreateBookDTO;
import example.com.Lab1Books.dto.UpdateBookDTO;
import example.com.Lab1Books.entity.BookEntity;
import example.com.Lab1Books.exception.ValidationException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class BookMapper {

    private BookMapper() {}

    public static BookDTO mapToResponse(BookEntity bookEntity) {
        Objects.requireNonNull(bookEntity, "BookEntity cannot be null");

        return new BookDTO(
                bookEntity.getId(),
                bookEntity.getAuthor(),
                bookEntity.getTitle(),
                bookEntity.getDescription(),
                bookEntity.getIsbn(),
                bookEntity.getGenre(),
                bookEntity.getReleaseDate());
    }

    public static BookEntity mapToEntityCreate(CreateBookDTO book) {
        Objects.requireNonNull(book, "Create book cannot be null");

        return new BookEntity(
                book.author(),
                book.title(),
                book.description(),
                book.isbn(),
                book.genre(),
                book.releaseDate()
        );
    }

    public static void mapToEntityUpdate(UpdateBookDTO book, BookEntity existingBook) {
        Objects.requireNonNull(existingBook, "Existing book cannot be null");

        if(book == null) {
            throw new ValidationException("UpdateBookDTO cannot be null when updating an entity.");
        }
        if (book.author() != null && !book.author().isBlank()) existingBook.setAuthor(book.author());
        if (book.title() != null && !book.title().isBlank()) existingBook.setTitle(book.title());
        if (book.description() != null && !book.description().isBlank()) existingBook.setDescription(book.description());
        if (book.isbn() != null && !book.isbn().isBlank()) existingBook.setIsbn(book.isbn());
        if (book.genre() != null && !book.genre().isBlank()) existingBook.setGenre(book.genre());
        if (book.releaseDate() != null) existingBook.setReleaseDate(book.releaseDate());
    }

    public static BookCustomPage<BookDTO> mapToPage(List<BookEntity> books, long totalElements, int currentPage, int pageSize) {
        List<BookDTO> bookResponses = books
                .stream()
                .map(BookMapper::mapToResponse)
                .collect(Collectors.toList());

        return new BookCustomPage<>(bookResponses, totalElements, currentPage, pageSize);

    }

}
