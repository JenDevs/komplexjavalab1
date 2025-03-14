package example.com.Lab1Books.exception.mapper;

import example.com.Lab1Books.exception.ValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException validation) {

        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\": \"" + validation.getMessage() + "\"}")
                .build();
    }


}
