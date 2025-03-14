package example.com.Lab1Books.service;

import example.com.Lab1Books.BookCustomPage;
import example.com.Lab1Books.mapper.BookMapper;
import example.com.Lab1Books.BookRepository;
import example.com.Lab1Books.dto.BookDTO;
import example.com.Lab1Books.dto.CreateBookDTO;
import example.com.Lab1Books.dto.UpdateBookDTO;
import example.com.Lab1Books.entity.BookEntity;
import example.com.Lab1Books.exception.*;

import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Response;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Inject
    public BookServiceImpl(BookRepository bookRepository) {
        this.repository = bookRepository;
    }

    @Override
    public BookDTO getBookById(@NotNull @Min(1) Long id) {
        return repository.findById(id)
                .map(BookMapper::mapToResponse)
                .orElseThrow(() -> new NotFoundException("Book with ID " + id + " not found"));
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(BookMapper::mapToResponse)
                .toList();
    }

    @Override
    public BookCustomPage<BookDTO> filterByAuthor(@NotBlank String author, @Min(1) int page, @Min(1) int size) {
        PageRequest pageRequest = PageRequest.ofPage(page, size, true);
        Page<BookEntity> books = repository.findByAuthorContainingIgnoreCase(author, pageRequest);

        if (books.totalElements() == 0) {
            return new BookCustomPage<>(Collections.emptyList(), 0, page, size, "No author with name " + author + " found.");
        }

        return BookMapper.mapToPage(books.content(), books.totalElements(), page, size);
    }

    @Override
    public BookCustomPage<BookDTO> filterByTitle(@NotBlank String title, @Min(1) int page, @Min(1) int size) {
        PageRequest pageRequest = PageRequest.ofPage(page, size, true);
        Page<BookEntity> books = repository.findByTitleContainingIgnoreCase(title, pageRequest);

        if (books.totalElements() == 0) {
            return new BookCustomPage<>(Collections.emptyList(), 0, page, size, "No title with name " + title + " found.");
        }

        return BookMapper.mapToPage(books.content(), books.totalElements(), page, size);
    }

    @Override
    public BookCustomPage<BookDTO> filterByGenre(@NotBlank String genre, @Min(1) int page, @Min(1) int size) {
        PageRequest pageRequest = PageRequest.ofPage(page, size, true);
        Page<BookEntity> books = repository.findByGenreContainingIgnoreCase(genre, pageRequest);

        if (books.totalElements() == 0) {
            return new BookCustomPage<>(Collections.emptyList(), 0, page, size, "No genre with name " + genre + " found.");
        }

        return BookMapper.mapToPage(books.content(), books.totalElements(), page, size);
    }


    @Override
    public BookCustomPage<BookDTO> filterByAuthorAndTitle(@NotBlank String author, @NotBlank String title, @Min(1) int page, @Min(1) int size) {
        PageRequest pageRequest = PageRequest.ofPage(page, size, true);
        Page<BookEntity> books = repository.findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(author, title, pageRequest);

        if (books.totalElements() == 0) {
            return new BookCustomPage<>(Collections.emptyList(), 0, page, size, "No author with name " + author + " and title " + title + " found.");
        }

        return BookMapper.mapToPage(books.content(), books.totalElements(), page, size);
    }

    @Override
    public BookCustomPage<BookDTO> filterByAuthorAndGenre(@NotBlank String author, @NotBlank String genre, @Min(1) int page,@Min(1) int size) {
        PageRequest pageRequest = PageRequest.ofPage(page, size, true);
        Page<BookEntity> books = repository.findByAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(author, genre, pageRequest);

        if (books.totalElements() == 0) {
            return new BookCustomPage<>(Collections.emptyList(), 0, page, size, "No author with name " + author + " and genre " + genre + " found.");
        }

        return BookMapper.mapToPage(books.content(), books.totalElements(), page, size);
    }


    @Override
    @Transactional
    public BookEntity createBook(@Valid @NotNull CreateBookDTO book) {
        if(repository.findByTitleAndAuthor(book.title(), book.author()).isPresent()) {
            throw new ConflictException("A book with title: " + book.title() + "' and author: '" + book.author() + "' already exists.");
        }

        if (findByIsbn(book.isbn()).isPresent()) {
            throw new ConflictException("A book with ISBN: '" + book.isbn() + "' already exists.");
        }

        try {
            var newBook = BookMapper.mapToEntityCreate(book);
            return repository.save(newBook);
        } catch (PersistenceException e) {
            throw new DatabaseException("A database error occurred during create book", e);
        }

    }

    @Override
    public Optional<BookEntity> findByIsbn(@NotNull @NotBlank String isbn) {
        return repository.findByIsbn(isbn);

    }


    @Override
    @Transactional
    public BookDTO updateBook(@Valid @NotNull UpdateBookDTO book, @NotNull @Min(1) Long id) {
        var existingBook = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot update: Book with ID " + id + " does not exist"));

        try {
            BookMapper.mapToEntityUpdate(book, existingBook);
            repository.save(existingBook);
            return BookMapper.mapToResponse(existingBook);
        } catch (PersistenceException e) {
            throw new DatabaseException("A database error occurred during update book", e);

        }

    }

    @Override
    @Transactional
    public Response deleteBook(@NotNull @Min(1) Long id) {
        var book = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot delete: Book with ID " + id + " not found"));
        try {
            repository.delete(book);
            return Response.ok(Map.of("message", String.format("Book with ID %d was successfully deleted.", id))).build();
        } catch (PersistenceException e) {
            throw new DatabaseException("A database error occurred during delete book", e);
        }
    }
}

