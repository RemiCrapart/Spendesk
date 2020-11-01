package com.spendesk.WalletCards.error;

import org.springframework.http.HttpStatus;

class ApiError {

  private String errorId;

  private HttpStatus httpStatus;

  private String message;

  private ApiError() {}

  public ApiError(String message, String errorId, HttpStatus httpStatus) {
    this.message = message;
    this.errorId = errorId;
    this.httpStatus = httpStatus;
  }

  public String getMessage() {
    return message;
  }

  public ApiError setMessage(String message) {
    this.message = message;
    return this;
  }

  public String getErrorId() {
    return errorId;
  }

  public ApiError setErrorId(String errorId) {
    this.errorId = errorId;
    return this;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
