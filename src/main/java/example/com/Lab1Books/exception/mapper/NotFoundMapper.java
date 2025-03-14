package example.com.Lab1Books.exception.mapper;

import example.com.Lab1Books.exception.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;

@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {


    @Override
    public Response toResponse(NotFoundException notFound) {

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(Map.of("error",notFound.getMessage()))
                .build();
    }
}
