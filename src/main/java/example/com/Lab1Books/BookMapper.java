package example.com.Lab1Books;

import example.com.Lab1Books.dto.BookResponse;
import example.com.Lab1Books.dto.CreateBook;
import example.com.Lab1Books.entity.BookEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookMapper {

    private BookMapper() {}

    public static BookResponse map(BookEntity bookEntity) {
        if( null == bookEntity )
            return null;
        return new BookResponse(bookEntity.getId(), bookEntity.getAuthor(), bookEntity.getTitle(), bookEntity.getDescription(),bookEntity.getIsbn(), bookEntity.getReleaseDate());
    }

    public static BookEntity map(CreateBook book) {
        if( null == book )
            return null;
        return new BookEntity(book.author(), book.title(), book.description(), book.isbn(), book.releaseDate());

//        BookEntity newBook = new BookEntity();
//        newBook.setTitle(book.title());
//        newBook.setAuthor(book.author());
//        newBook.setDescription(book.description());
//        newBook.setIsbn(book.isbn());
//        newBook.setReleaseDate(book.releaseDate());
//        return newBook;
    }

}
