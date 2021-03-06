package com.spendesk.WalletCards.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(value = SpendeskException.class)
  public ResponseEntity<Object> exception(SpendeskException exception) {
    return new ResponseEntity<>(
        new ApiError(exception.getMessage(), exception.getCode(), exception.getHttpStatus()),
        exception.getHttpStatus());
  }

  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException exception) {

    return new ResponseEntity<>(
        new ApiError(exception.getMessage(), "SPENTEC01", HttpStatus.BAD_REQUEST),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = MissingRequestHeaderException.class)
  protected ResponseEntity<Object> handleMissingRequestHeaderException(
      MissingRequestHeaderException exception) {

    return new ResponseEntity<>(
        new ApiError(exception.getMessage(), "SPENTEC01", HttpStatus.BAD_REQUEST),
        HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValidException(
          MethodArgumentNotValidException exception) {

    return new ResponseEntity<>(
            new ApiError(exception.getMessage(), "SPENTEC01", HttpStatus.BAD_REQUEST),
            HttpStatus.BAD_REQUEST);
  }
}


