package com.auth.gateway.exception;

public class AccessTokenInvalidException extends RuntimeException {
  public AccessTokenInvalidException(String message) {
    super(message);
  }
}
