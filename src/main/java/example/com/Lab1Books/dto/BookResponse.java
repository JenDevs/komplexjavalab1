package example.com.Lab1Books.dto;

import example.com.Lab1Books.entity.BookEntity;

import java.awt.print.Book;
import java.time.LocalDate;

/**
 * DTO for {@link example.com.Lab1Books.entity.BookEntity}
 */

// gör samma för create, och genom mapToEntity, och sedan resource.
public record BookResponse(Long id, String author, String title, String description, String isbn, LocalDate releaseDate) {

    public BookResponse(BookEntity bookEntity) {
        this(bookEntity.getId(),bookEntity.getAuthor(),bookEntity.getTitle(),bookEntity.getDescription(),bookEntity.getIsbn(),bookEntity.getReleaseDate());
    }


//    public static BookResponse mapToEntity(BookEntity bookEntity) {
//        return new BookResponse(bookEntity.getId(),bookEntity.getAuthor(),bookEntity.getTitle(),bookEntity.getDescription(),bookEntity.getIsbn(),bookEntity.getReleaseDate());
//    }
}
