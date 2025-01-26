package com.auth.gateway.exception;

public class NotUserException extends RuntimeException {
  public NotUserException(String message) {
    super(message);
  }
}
