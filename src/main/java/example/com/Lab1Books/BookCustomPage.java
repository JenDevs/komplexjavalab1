package example.com.Lab1Books;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BookCustomPage<T> {

    @JsonbProperty("content")
    @NotNull(message = "Content cannot be null")
    private List<T> content;

    @JsonbProperty("totalElements")
    @Min(value = 0, message = "Total elements cannot be negative")
    private long totalElements;

    @JsonbProperty("totalPages")
    @Min(value = 1, message = "Page number must be at least 1")
    private long totalPages;

    @JsonbProperty("currentPage")
    @Min(value = 1, message = "Page size must be at least 1")
    private int currentPage;

    @JsonbProperty("pageSize")
    @Min(value = 1)
    private int pageSize;

    @JsonbProperty("message")
    private String message;


    public BookCustomPage(List<T> content, long totalElements, int currentPage, int pageSize) {
        this.content = Objects.requireNonNullElse(content, Collections.emptyList());
        this.totalElements = Math.max(totalElements, 0);
        this.pageSize = Math.max(pageSize, 1);
        this.currentPage = Math.max(currentPage,1);
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
        this.message = null;
    }

    public BookCustomPage(List<T> content, long totalElements, long totalPages, int currentPage, int pageSize) {
        this.content = Collections.emptyList();
        this.totalElements = 0;
        this.pageSize = 1;
        this.currentPage = 1;
        this.totalPages = 10;

    }

    public BookCustomPage(List<T> content, long totalElements, int currentPage, int pageSize, String message) {
        this(content, totalElements, currentPage, pageSize);
        this.message = message;
    }

    public static <T> BookCustomPage<T> empty(int page, int size, String message) {
        return new BookCustomPage<T>(Collections.emptyList(), 0, page, size, message);
    }

    public List<T> getContent() {
        return content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getMessage() {
        return message;
    }


}
