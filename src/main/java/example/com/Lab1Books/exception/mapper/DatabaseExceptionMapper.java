package example.com.Lab1Books.exception.mapper;

import example.com.Lab1Books.exception.DatabaseException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.Map;

public class DatabaseExceptionMapper implements ExceptionMapper<DatabaseException> {

    @Override
    public Response toResponse(DatabaseException database) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of("error",database.getMessage()))
                .build();
    }

}
