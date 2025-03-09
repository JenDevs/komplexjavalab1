package example.com.Lab1Books;

import example.com.Lab1Books.dto.ResponseDto;
import example.com.Lab1Books.dto.BookResponse;
import example.com.Lab1Books.dto.CreateBook;
import example.com.Lab1Books.dto.UpdateBook;
import example.com.Lab1Books.entity.BookEntity;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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

    // getOneBook
    //"http://localhost:8080/api/books/4"
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookResponse getOneBook(@PathParam("id") Long id) {
        return bookService.getBookById(id);

    }


    // Hämta alla böcker
    //"http://localhost:8080/api/books"
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        return Response.ok(new ResponseDto(bookService.getAllBooks())).build();

//        return new ResponseDto(bookService.getAllBooks()); ändra public Response till public ResonseDto också
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(@Valid CreateBook book) {
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
    public Response updateBook(@Valid UpdateBook book, @PathParam("id") Long id) {
        bookService.updateBook(book, id);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        bookService.deleteBook(id);
        return Response.noContent().build();
    }


    @GET
    @Path("search/author")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterByAuthor(@QueryParam("author") String author) {
        List<BookResponse> filteredBooks = bookService.filterByAuthor(author);
        return Response.ok(filteredBooks).build();
    }


    @GET
    @Path("search/title-author")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterByTitleAndAuthor(@QueryParam("title") String title, @QueryParam("author") String author) {
        List<BookResponse> filteredBooks = bookService.filterByTitleAndAuthor(title, author);
        return Response.ok(filteredBooks).build();
    }

    @GET
    @Path("search/author-genre")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filterByAuthorAndGenre(@QueryParam("author") String author, @QueryParam("genre") String genre) {
        List<BookResponse> filteredBooks = bookService.filterByAuthorAndGenre(author, genre);
        return Response.ok(filteredBooks).build();
    }

}
