package com.auth.gateway.exception;

public class RefreshTokenInvalidException extends RuntimeException {
  public RefreshTokenInvalidException(String message) {
    super(message);
  }
}
