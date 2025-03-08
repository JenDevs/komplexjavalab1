package example.com.Lab1Books.exception;

public class NotFound extends RuntimeException {

  public NotFound() {
    super();
  }

  public NotFound(String message) {
    super(message);
  }
}
