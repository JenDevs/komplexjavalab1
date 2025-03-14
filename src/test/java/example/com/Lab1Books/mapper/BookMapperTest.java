package example.com.Lab1Books.mapper;

import example.com.Lab1Books.BookCustomPage;
import example.com.Lab1Books.dto.BookDTO;
import example.com.Lab1Books.dto.CreateBookDTO;
import example.com.Lab1Books.dto.UpdateBookDTO;
import example.com.Lab1Books.entity.BookEntity;
import example.com.Lab1Books.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {


    @Test
    @DisplayName("Should map create BookDTO to entity")
    void shouldMapCreateBookDtoToEntity() {
        CreateBookDTO createDTO = new CreateBookDTO("George Orwell", "1984", "Dystopian Novell", "0123456789","Dystopian", LocalDate.of(1999, 1, 1));
        BookEntity bookEntity = BookMapper.mapToEntityCreate(createDTO);

        assertEquals(createDTO.author(), bookEntity.getAuthor());
        assertEquals(createDTO.title(), bookEntity.getTitle());
        assertEquals(createDTO.isbn(), bookEntity.getIsbn());
    }


    @Test
    @DisplayName("Should bap book to entityDTO")
    void shouldMapBookToEntityDto() {
        BookEntity bookEntity = new BookEntity("George Orwell", "1984", "Dystopian Novell", "0123456789","Dystopian", LocalDate.of(1999, 1, 1));
        BookDTO bookDTO = BookMapper.mapToResponse(bookEntity);

        assertEquals(bookEntity.getAuthor(), bookDTO.author());
        assertEquals(bookEntity.getTitle(), bookDTO.title());
        assertEquals(bookEntity.getIsbn(), bookDTO.isbn());
    }

    @Test
    @DisplayName("Map to entity update should not throw exception when book is not null")
    void mapToEntityUpdateShouldNotThrowExceptionWhenBookIsNotNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        assertDoesNotThrow(() -> BookMapper.mapToEntityUpdate(updateBookDTO, existingBook));
    }

    @Test
    @DisplayName("Map to entity update should throw validation exception when book is null")
    void mapToEntityUpdateShouldThrowValidationExceptionWhenBookIsNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));

        ValidationException exception = assertThrows(ValidationException.class, () ->
                BookMapper.mapToEntityUpdate(null, existingBook)
        );

        assertEquals("UpdateBookDTO cannot be null when updating an entity.", exception.getMessage());
    }


    @Test
    @DisplayName("Map to entity update should not update when author is null")
    void mapToEntityUpdateShouldNotUpdateWhenAuthorIsNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO(null, "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("George Orwell", existingBook.getAuthor());
    }

    @Test
    @DisplayName("Map to entity update should update when author is not null")
    void mapToEntityUpdateShouldUpdateWhenAuthorIsNotNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("New Author", existingBook.getAuthor());
    }

    @Test
    @DisplayName("Map to entity update should not update when title is null")
    void mapToEntityUpdateShouldNotUpdateWhenTitleIsNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", null, "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("1984", existingBook.getTitle());
    }

    @Test
    @DisplayName("Map to entity update should update when title is not null")
    void mapToEntityUpdateShouldUpdateWhenTitleIsNotNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("New Title", existingBook.getTitle());
    }

    @Test
    @DisplayName("Map to entity update should not update when description is null")
    void mapToEntityUpdateShouldNotUpdateWhenDescriptionIsNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", null, "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("Dystopian Novel", existingBook.getDescription());
    }

    @Test
    @DisplayName("Map to entity update should update when description is not null")
    void mapToEntityUpdateShouldUpdateWhenDescriptionIsNotNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("New Description", existingBook.getDescription());
    }

    @Test
    @DisplayName("Map to entity update should not update when isbn is null")
    void mapToEntityUpdateShouldNotUpdateWhenIsbnIsNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", null, "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("0123456789", existingBook.getIsbn());
    }

    @Test
    @DisplayName("Map to entity update should update when isbn is not null")
    void mapToEntityUpdateShouldUpdateWhenIsbnIsNotNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("9876543210", existingBook.getIsbn());
    }

    @Test
    @DisplayName("Map to entity update should not update when genre is null")
    void mapToEntityUpdateShouldNotUpdateWhenGenreIsNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", null, LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("Dystopian", existingBook.getGenre());
    }

    @Test
    @DisplayName("Map to entity update should update when genre is not null")
    void mapToEntityUpdateShouldUpdateWhenGenreIsNotNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("New Genre", existingBook.getGenre());
    }


    @Test
    @DisplayName("Map to entity update should not update when release date is null")
    void mapToEntityUpdateShouldNotUpdateWhenReleaseDateIsNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", null);

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals(LocalDate.of(1999, 1, 1), existingBook.getReleaseDate());
    }

    @Test
    @DisplayName("Map to entity update should update when release date is not null")
    void mapToEntityUpdateShouldUpdateWhenReleaseDateIsNotNull() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals(LocalDate.of(2000, 2, 2), existingBook.getReleaseDate());
    }

    @Test
    @DisplayName("Map to entity update should not update when author is blank")
    void mapToEntityUpdateShouldNotUpdateWhenAuthorIsBlank() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novell", "0123456789","Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("", "New Title", "New Description", "9876543210","New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("George Orwell", existingBook.getAuthor());
    }


    @Test
    @DisplayName("Map to entity update should update when author is not blank")
    void mapToEntityUpdateShouldUpdateWhenAuthorIsNotBlank() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novell", "0123456789","Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210","New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);

        assertEquals("New Author", existingBook.getAuthor());
    }


    @Test
    @DisplayName("Map to entity update should not update when title is blank")
    void mapToEntityUpdateShouldNotUpdateWhenTitleIsBlank() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("1984", existingBook.getTitle());
    }

    @Test
    @DisplayName("Map to entity update should update when title is not blank")
    void mapToEntityUpdateShouldUpdateWhenTitleIsNotBlank() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("New Title", existingBook.getTitle());
    }

    @Test
    @DisplayName("Map to entity update should not update when description is blank")
    void mapToEntityUpdateShouldNotUpdateWhenDescriptionIsBlank() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("Dystopian Novel", existingBook.getDescription());
    }

    @Test
    @DisplayName("Map to entity update should update when description is not blank")
    void mapToEntityUpdateShouldUpdateWhenDescriptionIsNotBlank() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("New Description", existingBook.getDescription());
    }

    @Test
    @DisplayName("Map to entity update should not update when isbn is blank")
    void mapToEntityUpdateShouldNotUpdateWhenIsbnIsBlank() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("0123456789", existingBook.getIsbn());
    }

    @Test
    @DisplayName("Map to entity update should update when isbn is not blank")
    void mapToEntityUpdateShouldUpdateWhenIsbnIsNotBlank() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("9876543210", existingBook.getIsbn());
    }

    @Test
    @DisplayName("Map to entity update should not update when genre is blank")
    void mapToEntityUpdateShouldNotUpdateWhenGenreIsBlank() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("Dystopian", existingBook.getGenre());
    }

    @Test
    @DisplayName("Map to entity update should update when genre is not blank")
    void mapToEntityUpdateShouldUpdateWhenGenreIsNotBlank() {
        BookEntity existingBook = new BookEntity("George Orwell", "1984", "Dystopian Novel", "0123456789", "Dystopian", LocalDate.of(1999, 1, 1));
        UpdateBookDTO updateBookDTO = new UpdateBookDTO("New Author", "New Title", "New Description", "9876543210", "New Genre", LocalDate.of(2000, 2, 2));

        BookMapper.mapToEntityUpdate(updateBookDTO, existingBook);
        assertEquals("New Genre", existingBook.getGenre());
    }


    @Test
    @DisplayName("Map to page should map entities to DTOs correctly")
    void mapToPageShouldMapEntitiesToDTOsCorrectly() {
        List<BookEntity> bookEntities = List.of(
                new BookEntity("Author One", "Title One", "Description One", "1234567890", "Genre One", LocalDate.of(2001, 1, 1)),
                new BookEntity("Author Two", "Title Two", "Description Two", "0987654321", "Genre Two", LocalDate.of(2002, 2, 2))
        );

        long totalElements = bookEntities.size();
        int currentPage = 1;
        int pageSize = 5;

        BookCustomPage<BookDTO> result = BookMapper.mapToPage(bookEntities, totalElements, currentPage, pageSize);

        assertEquals(2, result.getTotalElements()); // Totalt antal böcker
        assertEquals(1, result.getCurrentPage()); // Rätt sida
        assertEquals(5, result.getPageSize()); // Rätt sidstorlek
        assertEquals(2, result.getContent().size()); // Två objekt i listan
        assertEquals("Title One", result.getContent().get(0).title()); // Kontrollera första bokens titel
        assertEquals("Title Two", result.getContent().get(1).title()); // Kontrollera andra bokens titel
    }

    @Test
    @DisplayName("Map to page should return empty page for empty list")
    void mapToPageShouldReturnEmptyPageForEmptyList() {
        List<BookEntity> bookEntities = Collections.emptyList();
        long totalElements = 0;
        int currentPage = 1;
        int pageSize = 5;

        BookCustomPage<BookDTO> result = BookMapper.mapToPage(bookEntities, totalElements, currentPage, pageSize);

        assertTrue(result.getContent().isEmpty()); // Kontrollera att listan är tom
        assertEquals(0, result.getTotalElements()); // Totalt antal böcker är 0
        assertEquals(1, result.getCurrentPage()); // Rätt sida
        assertEquals(5, result.getPageSize()); // Rätt sidstorlek
    }

    @Test
    @DisplayName("Map to page should handle negative values gracefully")
    void mapToPageShouldHandleNegativeValuesGracefully() {
        List<BookEntity> bookEntities = Collections.emptyList();
        long totalElements = -5; // Negativt totalantal
        int currentPage = -1; // Negativ sida
        int pageSize = 0; // Ogiltig sidstorlek

        BookCustomPage<BookDTO> result = BookMapper.mapToPage(bookEntities, totalElements, currentPage, pageSize);

        assertTrue(result.getContent().isEmpty()); // Listan ska vara tom
        assertEquals(0, result.getTotalElements()); // Negativ total ska bli 0
        assertEquals(1, result.getCurrentPage()); // Negativ sida ska sättas till 1
        assertEquals(1, result.getPageSize()); // Sidstorlek 0 ska bli 1
    }

}