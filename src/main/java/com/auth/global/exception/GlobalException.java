package com.auth.global.exception;

import com.auth.auth.exception.MissingSignInParametersException;
import com.auth.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.auth.global.exception.ErrorCode.*;

@RestControllerAdvice
@Slf4j
public class GlobalException {
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(USER_NOT_FOUND));
  }

  @ExceptionHandler(MissingSignInParametersException.class)
  public ResponseEntity<ErrorResponse> handleMissingSignInParametersException(MissingSignInParametersException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(MISSING_SIGN_IN_PARAMETERS));
  }
}
