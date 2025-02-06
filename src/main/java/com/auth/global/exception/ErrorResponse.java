package com.auth.global.exception;

import java.time.LocalDateTime;

record ErrorDetail (
  Integer code,
  String errorCode
) {}

public record ErrorResponse(
        Boolean result,
        String message,
        ErrorDetail data,
        LocalDateTime timestamp) {
  public ErrorResponse(ErrorCode errorCode) {
    this(false, errorCode.getMessage(), new ErrorDetail(errorCode.getCode(), errorCode.name()), LocalDateTime.now());
  }
}
