package example.com.Lab1Books;

import example.com.Lab1Books.entity.BookEntity;

import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.*;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {

    @Find
    Optional<BookEntity> findById(Long id);

//    @Override
//    Stream<BookEntity> findAll();

    @Find
    Optional<BookEntity> findByIsbn(String isbn);

    @Find
    Page<BookEntity> findByAuthorContainingIgnoreCase(String author, PageRequest pageRequest);

    @Find
    Page<BookEntity> findByTitleContainingIgnoreCase(String title, PageRequest pageRequest);

    @Find
    Page<BookEntity> findByGenreContainingIgnoreCase(String genre, PageRequest pageRequest);

    @Find
    Optional<BookEntity> findByTitleAndAuthor(String title, String author);

    @Find
    Page<BookEntity> findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(String author, String title, PageRequest pageRequest);

    @Find
    Page<BookEntity> findByAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(String author, String genre, PageRequest pageRequest);

}
