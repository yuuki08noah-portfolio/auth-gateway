package com.auth.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

  // Bad Request: 400
  MISSING_SIGN_IN_PARAMETERS(400, "Missing sign in parameters"),

  // Not Found: 404
  USER_NOT_FOUND(404, "User not found"),

  // Conflict: 409
  USER_ALREADY_EXISTS(409, "User already exists");


  private Integer code;
  private String message;
}
