package com.auth.auth.exception;

public class AccessTokenAndRefreshTokenNotMatchesException extends Exception {
  public AccessTokenAndRefreshTokenNotMatchesException(String message) {
    super(message);
  }
}
