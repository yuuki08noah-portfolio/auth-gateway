package com.auth.global.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        Integer code,
        String errorCode,
        String message,
        LocalDateTime timestamp) {
  public ErrorResponse(ErrorCode errorCode) {
    this(errorCode.getCode(), errorCode.name(), errorCode.getMessage(), LocalDateTime.now());
  }
}
