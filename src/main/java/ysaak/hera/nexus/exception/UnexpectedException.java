package ysaak.hera.nexus.exception;

public class UnexpectedException extends RuntimeException {
  /**
   * Exception serial ID 
   */
  private static final long serialVersionUID = 1791232296575476408L;

  public UnexpectedException(String message) {
    super(message);
  }
  
  public UnexpectedException(String message, Throwable cause) {
    super(message, cause);
  }
}
