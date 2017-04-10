package ysaak.garde.exception;

public abstract class HeraException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -8751674237274165694L;
  
  public HeraException(String message) {
    super(message);
  }

  public HeraException(String message, Throwable cause) {
    super(message, cause);
  }
}
