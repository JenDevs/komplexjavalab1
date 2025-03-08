package example.com.Lab1Books;

import example.com.Lab1Books.dto.BookResponse;
import example.com.Lab1Books.dto.CreateBook;
import example.com.Lab1Books.dto.UpdateBook;
import example.com.Lab1Books.entity.BookEntity;
import example.com.Lab1Books.exception.NotFound;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static example.com.Lab1Books.BookMapper.*;

@ApplicationScoped
public class BookServiceImpl implements BookService {


    private BookRepository repository;
    private BookService service;

    @Inject
    public BookServiceImpl(BookRepository bookRepository, BookService service) {
        this.repository = bookRepository;
    }

   public BookServiceImpl(){

   }

   @Override
    public List<BookResponse> getAllBooks() {
        return StreamSupport.stream(repository.findAll().spliterator(),false)
                .map(BookMapper::map)
                .filter(Objects::nonNull)
                .toList();

//        return repository.findAll()
//                .map(BookResponse::new)
//                .filter(Objects::nonNull)
//                .toList();

    }

    @Override
    public BookResponse getBookById(Long id) {
        return repository.findById(id)
                .map(BookResponse::new)
                .orElseThrow(() -> new NotFound("Book with id " + id + " not found"));
    }

    @Override
    public BookEntity createBook(CreateBook book) {
        var newBook = map(book);
        newBook = repository.insert(newBook);
        return newBook;
    }

    @Override
    public void updateBook(UpdateBook book, Long id) {
        var oldBook = repository.findById(id).orElseThrow(() -> new NotFound("Book with id " + id + " not found"));
        if(book.title() != null)
            oldBook.setTitle(book.title());
        if(book.author() != null)
            oldBook.setAuthor(book.author());
        repository.update(oldBook);
    }



}
