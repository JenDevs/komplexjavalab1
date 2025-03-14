package example.com.Lab1Books.resource;

import example.com.Lab1Books.BookCustomPage;
import example.com.Lab1Books.dto.*;
import example.com.Lab1Books.entity.BookEntity;
import example.com.Lab1Books.service.BookService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;

import java.util.List;


@Path("books")
@Log
public class BookResource {

    private BookService bookService;

    @Inject
    public BookResource(BookService booksService) {
        this.bookService = booksService;
    }

    public BookResource() { }


    //"http://localhost:8080/api/books/4"
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookDTO getOneBook(@PathParam("id") @NotNull @Min(1) Long id) {
        return bookService.getBookById(id);

    }



    //"http://localhost:8080/api/books"
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return Response.ok(books).build();

    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(@Valid @NotNull CreateBookDTO book) {
        BookEntity newBook = bookService.createBook(book);
        return Response
                .status(Response.Status.CREATED)
                .header("Location", "/api/books/" + newBook.getId())
                .entity(newBook)
                .build();
    }


    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@Valid @NotNull UpdateBookDTO book, @PathParam("id") @NotNull @Min(1) Long id) {
        BookDTO updatedBook = bookService.updateBook(book, id);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") @NotNull @Min(1) Long id) {
        return bookService.deleteBook(id);
    }


    @GET
    @Path("search/author")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterByAuthor(
            @QueryParam("author") @NotBlank @NotNull String author,
            @QueryParam("page") @DefaultValue("1") @Min(1) Integer page,
            @QueryParam("size") @DefaultValue("5") @Min(1) Integer size) {

        BookCustomPage<BookDTO> filteredBooks = bookService.filterByAuthor(author, page, size);
        return Response.ok(filteredBooks).build();
    }


    @GET
    @Path("search/title")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterByTitle(
            @QueryParam("title") @NotNull @NotBlank String title,
            @QueryParam("page") @DefaultValue("1") @Min(1) Integer page,
            @QueryParam("size") @DefaultValue("5") @Min(1) Integer size) {

        BookCustomPage<BookDTO> filteredBooks = bookService.filterByTitle(title, page, size);
        return Response.ok(filteredBooks).build();
    }


    @GET
    @Path("search/genre")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterByGenre(
            @QueryParam("genre") @NotNull @NotBlank String genre,
            @QueryParam("page") @DefaultValue("1") @Min(1) Integer page,
            @QueryParam("size") @DefaultValue("5") @Min(1) Integer size) {

        BookCustomPage<BookDTO> filteredBooks = bookService.filterByGenre(genre, page, size);
        return Response.ok(filteredBooks).build();
    }


    @GET
    @Path("search/author-title")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterByAuthorAndTitle(
            @QueryParam("author") @NotNull @NotBlank String author,
            @QueryParam("title") @NotNull @NotBlank String title,
            @QueryParam("page") @DefaultValue("1") @Min(1) Integer page,
            @QueryParam("size") @DefaultValue("5") @Min(1) Integer size) {

        BookCustomPage<BookDTO> filteredBooks = bookService.filterByAuthorAndTitle(author, title, page, size);
        return Response.ok(filteredBooks).build();
    }

    @GET
    @Path("search/author-genre")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterByAuthorAndGenre(
            @QueryParam("author") @NotNull @NotBlank String author,
            @QueryParam("genre") @NotNull @NotBlank String genre,
            @QueryParam("page") @DefaultValue("1") @Min(1) Integer page,
            @QueryParam("size") @DefaultValue("5") @Min(1) Integer size) {

        BookCustomPage<BookDTO> filteredBooks = bookService.filterByAuthorAndGenre(author, genre, page, size);
        return Response.ok(filteredBooks).build();
    }

}
