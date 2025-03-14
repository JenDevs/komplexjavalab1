package example.com.Lab1Books.service;

import example.com.Lab1Books.BookCustomPage;
import example.com.Lab1Books.BookRepository;
import example.com.Lab1Books.dto.BookDTO;
import example.com.Lab1Books.dto.CreateBookDTO;
import example.com.Lab1Books.dto.UpdateBookDTO;
import example.com.Lab1Books.entity.BookEntity;
import example.com.Lab1Books.exception.ConflictException;
import example.com.Lab1Books.exception.DatabaseException;
import example.com.Lab1Books.exception.NotFoundException;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {


    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Get book by id should return book when found")
    void getBookByIdShouldReturnBookWhenFound() {
        Long bookId = 1L;
        BookEntity bookEntity = new BookEntity("Ursula K. Le Guin", "A Wizard of Earthsea", "A coming-of-age story about a young mage, Ged, and his journey to master his powers.", "0123456789", "Fantasy", LocalDate.of(1968, 9, 1));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));

        BookDTO result = bookService.getBookById(bookId);

        assertNotNull(result);
        assertEquals("Ursula K. Le Guin", result.author());
        assertEquals("A Wizard of Earthsea", result.title());
    }
    
    @Test
    @DisplayName("Get book by id should throw not found exception when missing")
    void getBookByIdShouldThrowNotFoundExceptionWhenMissing() {
        Long bookId = 42L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.getBookById(bookId));
    }
    
    @Test
    @DisplayName("Get all books should return list of books")
    void getAllBooksShouldReturnListOfBooks() {
        List<BookEntity> books = List.of(
                new BookEntity("Ursula K. Le Guin", "A Wizard of Earthsea",
                        "A coming-of-age story about a young mage, Ged, and his journey " +
                                "to master his powers.", "9780553383041",
                        "Fantasy", LocalDate.of(1968, 9, 1)),
                new BookEntity("J.R.R. Tolkien", "The Hobbit",
                "A fantasy novel about Bilbo Baggins", "9780345339683",
                        "Fantasy", LocalDate.of(1937, 9, 21)));


        when(bookRepository.findAll()).thenReturn(books.stream());
        List<BookDTO> result = bookService.getAllBooks();
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("Ursula K. Le Guin", result.get(0).author());
        assertEquals("J.R.R. Tolkien", result.get(1).author());


    }

    @Test
    @DisplayName("Filter by author should return books when found")
    void filterByAuthorShouldReturnBooksWhenFound() {
        List<BookEntity> books = List.of(
                new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian Fiction", LocalDate.of(1949, 6, 8)),
                new BookEntity("George Orwell", "Animal Farm", "Satirical Novel", "9876543210", "Political Satire", LocalDate.of(1945, 8, 17)));

        Page<BookEntity> mockPage = mock(Page.class);
        when(mockPage.content()).thenReturn(books);
        when(mockPage.totalElements()).thenReturn((long) books.size());

        when(bookRepository.findByAuthorContainingIgnoreCase(eq("George Orwell"), any(PageRequest.class)))
                .thenReturn(mockPage);

        BookCustomPage<BookDTO> result = bookService.filterByAuthor("George Orwell", 1, 5);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("George Orwell", result.getContent().getFirst().author());
    }

    @Test
    @DisplayName("Filter by author should return empty page when no books found")
    void filterByAuthorShouldReturnEmptyPageWhenNoBooksFound() {
        when(bookRepository.findByAuthorContainingIgnoreCase(eq("Unknown Author"), any(PageRequest.class)))
                .thenReturn(mock(Page.class));

        BookCustomPage<BookDTO> result = bookService.filterByAuthor("Unknown Author", 1, 5);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        assertEquals("No author with name Unknown Author found.", result.getMessage());
    }

    @Test
    @DisplayName("Filter by title should return books when found")
    void filterByTitleShouldReturnBooksWhenFound() {
        List<BookEntity> books = List.of(
                new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian Fiction", LocalDate.of(1949, 6, 8)));

        Page<BookEntity> mockPage = mock(Page.class);
        when(mockPage.content()).thenReturn(books);
        when(mockPage.totalElements()).thenReturn((long) books.size());

        when(bookRepository.findByTitleContainingIgnoreCase(eq("1984"), any(PageRequest.class)))
                .thenReturn(mockPage);

        BookCustomPage<BookDTO> result = bookService.filterByTitle("1984", 1, 5);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("1984", result.getContent().getFirst().title());

    }

    @Test
    @DisplayName("Filter by title should return empty page when no books found")
    void filterByTitleShouldReturnEmptyPageWhenNoBooksFound() {
        when(bookRepository.findByTitleContainingIgnoreCase(eq("Unknown Title"), any(PageRequest.class)))
                .thenReturn(mock(Page.class));

        BookCustomPage<BookDTO> result = bookService.filterByTitle("Unknown Title", 1, 5);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        assertEquals("No title with name Unknown Title found.", result.getMessage());

    }

    @Test
    @DisplayName("Filter by genre should return books when found")
    void filterByGenreShouldReturnBooksWhenFound() {
        List<BookEntity> books = List.of(
                new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian Fiction", LocalDate.of(1949, 6, 8)));

        Page<BookEntity> mockPage = mock(Page.class);
        when(mockPage.content()).thenReturn(books);
        when(mockPage.totalElements()).thenReturn((long) books.size());

        when(bookRepository.findByGenreContainingIgnoreCase(eq("Dystopian Fiction"), any(PageRequest.class)))
                .thenReturn(mockPage);

        BookCustomPage<BookDTO> result = bookService.filterByGenre("Dystopian Fiction", 1, 5);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Dystopian Fiction", result.getContent().getFirst().genre());

    }

    @Test
    @DisplayName("Filter by genre should return empty list when no books found")
    void filterByGenreShouldReturnEmptyListWhenNoBooksFound() {
        when(bookRepository.findByGenreContainingIgnoreCase(eq("Unknown Genre"), any(PageRequest.class)))
                .thenReturn(mock(Page.class));

        BookCustomPage<BookDTO> result = bookService.filterByGenre("Unknown Genre", 1, 5);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        assertEquals("No genre with name Unknown Genre found.", result.getMessage());
    }

    @Test
    @DisplayName("Filter by author and title should return books when found")
    void filterByAuthorAndTitleShouldReturnBooksWhenFound() {
        List<BookEntity> books = List.of(
                new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian Fiction", LocalDate.of(1949, 6, 8)));

        Page<BookEntity> mockPage = mock(Page.class);
        when(mockPage.content()).thenReturn(books);
        when(mockPage.totalElements()).thenReturn((long) books.size());

        when(bookRepository.findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(eq("George Orwell"), eq("1984"), any(PageRequest.class)))
                .thenReturn(mockPage);

        BookCustomPage<BookDTO> result = bookService.filterByAuthorAndTitle("George Orwell", "1984", 1, 5);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("George Orwell", result.getContent().getFirst().author());
        assertEquals("1984", result.getContent().getFirst().title());
    }

    @Test
    @DisplayName("Filter by author and title should return empty list when no books found")
    void filterByAuthorAndTitleShouldReturnEmptyListWhenNoBooksFound() {
        when(bookRepository.findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(eq("George Orwell"), eq("1984"), any(PageRequest.class)))
                .thenReturn(mock(Page.class));

        BookCustomPage<BookDTO> result = bookService.filterByAuthorAndTitle("George Orwell", "1984", 1, 5);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        assertEquals("No author with name George Orwell and title 1984 found.", result.getMessage());

    }

    @Test
    @DisplayName("Filter by author and genre should return books when found")
    void filterByAuthorAndGenreShouldReturnBooksWhenFound() {
        List<BookEntity> books = List.of(
                new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian Fiction", LocalDate.of(1949, 6, 8)));

        Page<BookEntity> mockPage = mock(Page.class);
        when(mockPage.content()).thenReturn(books);
        when(mockPage.totalElements()).thenReturn((long) books.size());

        when(bookRepository.findByAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(eq("George Orwell"), eq("Dystopian Fiction"), any(PageRequest.class)))
                .thenReturn(mockPage);

        BookCustomPage<BookDTO> result = bookService.filterByAuthorAndGenre("George Orwell", "Dystopian Fiction", 1, 5);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("George Orwell", result.getContent().getFirst().author());
        assertEquals("Dystopian Fiction", result.getContent().getFirst().genre());

    }

    @Test
    @DisplayName("Filter byt author and genre should return empty list when no books found")
    void filterBytAuthorAndGenreShouldReturnEmptyListWhenNoBooksFound() {
        when(bookRepository.findByAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(eq("George Orwell"), eq("Dystopian Fiction"), any(PageRequest.class)))
                .thenReturn(mock(Page.class));

        BookCustomPage<BookDTO> result = bookService.filterByAuthorAndGenre("George Orwell", "Dystopian Fiction", 1, 5);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        assertEquals("No author with name George Orwell and genre Dystopian Fiction found.", result.getMessage());
        
    }
    
    @Test
    @DisplayName("Create book should create new book")
    void createBookShouldCreateNewBook() {
        CreateBookDTO bookDTO = new CreateBookDTO(
                "George Orwell", "1984", "Dystopian novel", "0123456789", "Dystopian Fiction", LocalDate.of(1949, 6, 8));
        BookEntity savedBook = new BookEntity(
                "George Orwell", "1984", "Dystopian novel", "0123456789", "Dystopian Fiction", LocalDate.of(1949, 6, 8));

        when(bookRepository.findByTitleAndAuthor(bookDTO.title(), bookDTO.author())).thenReturn(Optional.empty());
        when(bookRepository.findByIsbn(bookDTO.isbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(BookEntity.class))).thenReturn(savedBook);

        BookEntity result = bookService.createBook(bookDTO);

        assertNotNull(result);
        assertEquals(bookDTO.title(), result.getTitle());
        assertEquals(bookDTO.author(), result.getAuthor());
        assertEquals(bookDTO.isbn(), result.getIsbn());

        verify(bookRepository, times(1)).save(any(BookEntity.class));

    }
    
    @Test
    @DisplayName("Create book should throw conflict exception if author and title exist")
    void createBookShouldThrowConflictExceptionIfAuthorAndTitleExist() {
        CreateBookDTO bookDTO = new CreateBookDTO(
                "George Orwell", "1984", "Dystopian novel", "0123456789", "Dystopian Fiction", LocalDate.of(1949, 6, 8));

        when(bookRepository.findByTitleAndAuthor(bookDTO.title(), bookDTO.author()))
                .thenReturn(Optional.of(new BookEntity()));

        ConflictException exception = assertThrows(ConflictException.class, () -> bookService.createBook(bookDTO));
        assertEquals("A book with title: 1984' and author: 'George Orwell' already exists.", exception.getMessage());

        verify(bookRepository, never()).save(any(BookEntity.class));
    }

    @Test
    @DisplayName("Create book should throw conflict exception if isbn already exist")
    void createBookShouldThrowConflictExceptionIfIsbnAlreadyExist() {
        CreateBookDTO bookDTO = new CreateBookDTO(
                "George Orwell", "1984", "Dystopian novel", "0123456789", "Dystopian Fiction", LocalDate.of(1949, 6, 8));

        when(bookRepository.findByTitleAndAuthor(bookDTO.title(), bookDTO.author())).thenReturn(Optional.empty());
        when(bookRepository.findByIsbn(bookDTO.isbn())).thenReturn(Optional.of(new BookEntity()));

        ConflictException exception = assertThrows(ConflictException.class, () -> bookService.createBook(bookDTO));
        assertEquals("A book with ISBN: '0123456789' already exists.", exception.getMessage());

        verify(bookRepository, never()).save(any(BookEntity.class));

    }
    
    @Test
    @DisplayName("Create book should throw database exception if database fails")
    void createBookShouldThrowDatabaseExceptionIfDatabaseFails() {
        CreateBookDTO bookDTO = new CreateBookDTO(
                "George Orwell", "1984", "Dystopian novel", "0123456789", "Dystopian Fiction", LocalDate.of(1949, 6, 8));

        when(bookRepository.findByTitleAndAuthor(bookDTO.title(), bookDTO.author())).thenReturn(Optional.empty());
        when(bookRepository.findByIsbn(bookDTO.isbn())).thenReturn(Optional.empty());

        when(bookRepository.save(any(BookEntity.class))).thenThrow(new PersistenceException("DB error"));

        DatabaseException exception = assertThrows(DatabaseException.class, () -> bookService.createBook(bookDTO));
        assertEquals("A database error occurred during create book", exception.getMessage());

        verify(bookRepository, times(1)).save(any(BookEntity.class));
        
    }
    
    @Test
    @DisplayName("Update book should successfully update one existing book")
    void updateBookShouldSuccessfullyUpdateOneExistingBook() {
        Long bookId = 1L;
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian novel", "0123456789", "Fiction", LocalDate.of(1949, 6, 8));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 1, 1));

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        when(bookRepository.save(any(BookEntity.class))).thenReturn(existingBook);

        BookDTO result = bookService.updateBook(updateBookDTO, bookId);

        assertNotNull(result);
        assertEquals(updateBookDTO.author(), result.author());
        assertEquals(updateBookDTO.title(), result.title());
        assertEquals(updateBookDTO.isbn(), result.isbn());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(BookEntity.class));
        
    }

    @Test
    @DisplayName("Update book should throw not found exception if books not exist")
    void updateBookShouldThrowNotFoundExceptionIfBooksNotExist() {
        Long bookId = 42L;
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "0123456789", "New Genre", LocalDate.of(2000, 1, 1));

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookService.updateBook(updateBookDTO, bookId));
        assertEquals("Cannot update: Book with ID 42 does not exist", exception.getMessage());

        verify(bookRepository, never()).save(any(BookEntity.class));
    }
    
    @Test
    @DisplayName("Update book should throw database exception when data base fails during update")
    void updateBookShouldThrowDatabaseExceptionWhenDataBaseFailsDuringUpdate() {
        Long bookId = 42L;
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian novel", "0123456789", "Fiction", LocalDate.of(1949, 6, 8));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 1, 1));

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        when(bookRepository.save(any(BookEntity.class))).thenThrow(new PersistenceException("Database error"));

        DatabaseException exception = assertThrows(DatabaseException.class, () -> bookService.updateBook(updateBookDTO, bookId));
        assertEquals("A database error occurred during update book", exception.getMessage());

        verify(bookRepository, times(1)).save(any(BookEntity.class));
        
    }
    
    @Test
    @DisplayName("Delete book should return ok when books is successfully deleted")
    void deleteBookShouldReturnOkWhenBooksIsSuccessfullyDeleted() {
        Long bookId = 42L;
        BookEntity book = new BookEntity("George Orwell", "1984", "Dystopian Novel", "9780451524935", "Dystopian Fiction", LocalDate.of(1949, 6, 8));

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Response response = bookService.deleteBook(bookId);

        verify(bookRepository).delete(book);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        assertEquals("{message=Book with ID 42 was successfully deleted.}", response.getEntity().toString());
        
    }
    
    @Test
    @DisplayName("Delete book should throw not found exception when book does not exist")
    void deleteBookShouldThrowNotFoundExceptionWhenBookDoesNotExist() {
        Long bookId = 42L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookService.deleteBook(bookId));

        assertEquals("Cannot delete: Book with ID 42 not found", exception.getMessage());

        verify(bookRepository, never()).delete(any());
        
    }
    
    @Test
    @DisplayName("Delete books should trow database exception when database error occurs")
    void deleteBooksShouldTrowDatabaseExceptionWhenDatabaseErrorOccurs() {
        Long bookId = 42L;
        BookEntity book = new BookEntity("Aldous Huxley", "Brave New World", "Dystopian", "9780060850524", "Fiction", LocalDate.of(1932, 1, 1));

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        doThrow(new PersistenceException("Database error")).when(bookRepository).delete(book);

        DatabaseException exception = assertThrows(DatabaseException.class, () -> bookService.deleteBook(bookId));

        assertEquals("A database error occurred during delete book", exception.getMessage());

        verify(bookRepository).delete(book);
        
    }

    
}