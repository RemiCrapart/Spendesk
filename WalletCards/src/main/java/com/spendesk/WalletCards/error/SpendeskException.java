package com.spendesk.WalletCards.error;

import org.springframework.http.HttpStatus;

public class SpendeskException extends Exception {

  String message;

  String code;

  HttpStatus httpStatus;

  public SpendeskException(String message, String code, HttpStatus httpStatus) {
    this.message = message;
    this.code = code;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public SpendeskException setMessage(String message) {
    this.message = message;
    return this;
  }

  public String getCode() {
    return code;
  }

  public SpendeskException setCode(String code) {
    this.code = code;
    return this;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public SpendeskException setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
    return this;
  }
}
