package com.auth.gateway.exception;

public class NotAnonymousException extends RuntimeException {
  public NotAnonymousException(String message) {
    super(message);
  }
}
