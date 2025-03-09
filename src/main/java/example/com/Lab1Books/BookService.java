package example.com.Lab1Books;

import example.com.Lab1Books.dto.BookResponse;
import example.com.Lab1Books.dto.CreateBook;
import example.com.Lab1Books.dto.UpdateBook;
import example.com.Lab1Books.entity.BookEntity;

import java.util.List;

public interface BookService {
    BookResponse getBookById(Long id);
    List<BookResponse> getAllBooks();

    BookEntity createBook(CreateBook book);

    void updateBook(UpdateBook book, Long id);

    void deleteBook(Long id);

    // List<BookResponse>filterByAuthorAndTitle(String author, String title);

    List<BookResponse> filterByAuthor(String author);

    List<BookResponse> filterByTitleAndAuthor(String title, String author);

    List<BookResponse> filterByAuthorAndGenre(String author, String genre);

}


