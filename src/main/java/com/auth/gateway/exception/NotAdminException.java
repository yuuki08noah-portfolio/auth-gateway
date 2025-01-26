package com.auth.gateway.exception;

public class NotAdminException extends RuntimeException {
  public NotAdminException(String message) {
    super(message);
  }
}
