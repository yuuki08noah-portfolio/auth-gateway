package com.auth.auth.exception;

public class MissingSignInParametersException extends RuntimeException {
  public MissingSignInParametersException(String message) {
    super(message);
  }
}
