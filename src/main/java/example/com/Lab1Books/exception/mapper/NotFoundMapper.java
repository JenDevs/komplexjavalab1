package example.com.Lab1Books.exception.mapper;

import example.com.Lab1Books.exception.NotFound;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundMapper implements ExceptionMapper<NotFound> {

    @Override
    public Response toResponse(NotFound notFound) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(notFound.getMessage())
                .build();
    }
}
