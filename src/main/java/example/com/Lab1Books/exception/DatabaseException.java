package example.com.Lab1Books.exception;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
