package example.com.Lab1Books;

import example.com.Lab1Books.dto.BookResponse;
import example.com.Lab1Books.dto.CreateBook;
import example.com.Lab1Books.dto.UpdateBook;
import example.com.Lab1Books.entity.BookEntity;


public class BookMapper {

    private BookMapper() {}

    public static BookResponse mapToResponse(BookEntity bookEntity) {
        if( null == bookEntity )
            return null;
        return new BookResponse(bookEntity.getId(), bookEntity.getAuthor(), bookEntity.getTitle(), bookEntity.getDescription(),bookEntity.getIsbn(), bookEntity.getReleaseDate());
    }

    public static BookEntity mapToEntityCreate(CreateBook book) {
        if( null == book )
            return null;

        BookEntity newBook = new BookEntity();
        newBook.setTitle(book.title());
        newBook.setAuthor(book.author());
        newBook.setDescription(book.description());
        newBook.setIsbn(book.isbn());
        newBook.setReleaseDate(book.releaseDate());
        return newBook;
    }

    public static void mapToEntityUpdate(UpdateBook book, BookEntity existingBook) {
        if (book == null || existingBook == null) return;

        if (book.title() != null) existingBook.setTitle(book.title());
        if (book.author() != null) existingBook.setAuthor(book.author());
        if (book.description() != null) existingBook.setDescription(book.description());
        if (book.isbn() != null) existingBook.setIsbn(book.isbn());
        if (book.releaseDate() != null) existingBook.setReleaseDate(book.releaseDate());
    }

}
