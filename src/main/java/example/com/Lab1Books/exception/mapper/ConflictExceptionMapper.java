package example.com.Lab1Books.exception.mapper;

import example.com.Lab1Books.exception.ConflictException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.Map;

public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {

    @Override
    public Response toResponse(ConflictException conflict) {
        return Response.status(Response.Status.CONFLICT)
                .entity(Map.of("error",conflict.getMessage()))
                .build();
    }

}
