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

@Path("books")
@Log
// @Produces(MediaType.APPLICATION_JSON)
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


    // Hämta alla böker
    //"http://localhost:8080/api/books"
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        return Response.ok(new ResponseDto(bookService.getAllBooks())).build();

//        return new ResponseDto(bookService.getAllBooks()); ändra public Response till public ResonseDto också
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(@Valid CreateBook book) {
//        if (book == null) {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Book cannot be null").build();
//        }
        BookEntity newBook = bookService.createBook(book);
        log.info("New book created: " + newBook);
        return Response
                .status(Response.Status.CREATED)
                .header("Location", "/api/books" + newBook.getId())
                .build();
    }


    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@Valid UpdateBook book, @PathParam("id") Long id) {
        bookService.updateBook(book, id);
        return Response.ok().build();

//        return Response.notModified().build();
    }


    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBookFieldByField(@Valid UpdateBook book, @PathParam("id") Long id) {
        bookService.updateBook(book, id);
        return Response.ok().build();


//        return Response.notModified().build();

    }

    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        bookService.deleteBook(id);
        return Response.noContent().build();
    }
}
