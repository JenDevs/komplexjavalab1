package example.com.Lab1Books;

import example.com.Lab1Books.entity.BookEntity;
import jakarta.data.repository.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {

    @Find
    Optional<BookEntity> findById(Long id);

    @Find
    List<BookEntity> findByAuthorContainingIgnoreCase(String author);

    @Find
    List<BookEntity> findByTitleContainingIgnoreCase(String title);

    @Find
    List<BookEntity> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author);

    @Find
    List<BookEntity> findByAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(String author, String genre);

    // List<BookEntity> findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(String author, String title);
}
