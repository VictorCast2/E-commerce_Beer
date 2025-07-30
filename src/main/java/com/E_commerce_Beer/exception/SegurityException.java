package com.E_commerce_Beer.exception;

public class SegurityException extends RuntimeException {

  public SegurityException(String message) {
    super(message);
  }

  public SegurityException(String message, Throwable cause) {
    super(message, cause);
  }

  public SegurityException(Throwable cause) {
    super(cause);
  }

}