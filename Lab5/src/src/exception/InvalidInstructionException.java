package src.exception;

public class InvalidInstructionException extends Exception {
  private static final long serialVersionUID = 1L;

  public InvalidInstructionException(String s) {
    super(s);
  }
}
