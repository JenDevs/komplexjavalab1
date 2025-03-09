package example.com.Lab1Books;

import example.com.Lab1Books.dto.BookResponse;
import example.com.Lab1Books.dto.CreateBook;
import example.com.Lab1Books.dto.UpdateBook;
import example.com.Lab1Books.entity.BookEntity;
import example.com.Lab1Books.exception.NotFound;
import example.com.Lab1Books.exception.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Inject
    public BookServiceImpl(BookRepository bookRepository) {
        this.repository = bookRepository;
    }

    @Override
    public BookResponse getBookById(Long id) {
        return repository.findById(id)
                .map(BookMapper::mapToResponse)
                .orElseThrow(() -> new NotFound("Book with id " + id + " not found"));
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(BookMapper::mapToResponse)
                .filter(Objects::nonNull)
                .toList();
    }


    @Override
    public List<BookResponse> filterByAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new ValidationException("Author must be provided.");
        }

        List<BookEntity> books = repository.findByAuthorContainingIgnoreCase(author);
        return books.stream().map(BookMapper::mapToResponse).toList();
    }


    @Override
    public List<BookResponse> filterByTitleAndAuthor(String title, String author) {
        if (title == null || title.trim().isEmpty() || author == null || author.trim().isEmpty()) {
            throw new ValidationException("Both title and author must be provided.");
        }

        List<BookEntity> books = repository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);

        return books.stream()
                .map(BookMapper::mapToResponse)
                .toList();
    }

    @Override
    public List<BookResponse> filterByAuthorAndGenre(String author, String genre) {
        if (author == null || author.trim().isEmpty() || genre == null || genre.trim().isEmpty()) {
            throw new ValidationException("Both author and genre must be provided.");
        }

        List<BookEntity> filteredBooks = repository.findByAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(author, genre);

        return filteredBooks.stream()
                .map(BookMapper::mapToResponse)
                .toList();
    }


    @Override
    @Transactional
    public BookEntity createBook(CreateBook book) {
        boolean existingBook = !repository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(
                book.title(), book.author()).isEmpty();

        if (existingBook) {
            throw new ValidationException("Book with title: " + book.title() + " and author: " + book.author() + " already exists");
        }

        var newBook = BookMapper.mapToEntityCreate(book);
        return repository.save(newBook);
    }

    @Override
    @Transactional
    public void updateBook(UpdateBook book, Long id) {
        var existingBook = repository.findById(id)
                .orElseThrow(() -> new NotFound("Book with id " + id + " not found"));

        BookMapper.mapToEntityUpdate(book, existingBook);
        repository.save(existingBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        var book = repository.findById(id)
                .orElseThrow(() -> new NotFound("Book with id " + id + " not found"));
        repository.delete(book);
    }
}

