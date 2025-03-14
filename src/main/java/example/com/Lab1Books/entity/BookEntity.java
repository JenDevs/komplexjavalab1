package example.com.Lab1Books.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "book", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "author"})
})
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "author")
    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 255, message = "Author must be between 2 and 255 characters")
    private String author;

    @Column(name = "title")
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @Column(name = "description")
    @NotBlank(message = "Description is required")
    @Size(min =10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    @Column(name = "isbn", unique = true)
    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN must be 10 or 13 digits")
    private String isbn;

    @Column(name = "genre", nullable = false)
    @NotBlank(message = "Genre is required")
    @Size(min = 1, max = 50, message = "Genre must be between 1 and 50 characters")
    private String genre;

    @Column(name = "release_date", nullable = false)
    @NotNull(message = "Release date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    public BookEntity() {
    }

    public BookEntity(String author, String title, String description, String isbn, String genre, LocalDate releaseDate) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.releaseDate = releaseDate;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getGenre() {return genre;}

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

   // public void setId(Long id) { this.id = id;}


    public void setAuthor(String author) { this.author = author;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setGenre(String genre) {this.genre = genre;}

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }


    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isbn='" + isbn + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity bookEntity = (BookEntity) o;
        return getId() != null && Objects.equals(getId(), bookEntity.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

}

