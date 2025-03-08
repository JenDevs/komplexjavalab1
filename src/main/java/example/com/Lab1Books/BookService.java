package example.com.Lab1Books;

import example.com.Lab1Books.dto.BookResponse;
import example.com.Lab1Books.dto.CreateBook;
import example.com.Lab1Books.dto.UpdateBook;
import example.com.Lab1Books.entity.BookEntity;

import java.util.List;

public interface BookService {
    List<BookResponse> getAllBooks();
    BookResponse getBookById(Long id);
    BookEntity createBook(CreateBook book);
    void updateBook(UpdateBook book, Long id);
}


