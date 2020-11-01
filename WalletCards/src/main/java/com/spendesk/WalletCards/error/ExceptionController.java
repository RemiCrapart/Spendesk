package com.spendesk.WalletCards.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  Logger logger = LoggerFactory.getLogger(ExceptionController.class);

  @ExceptionHandler(value = SpendeskException.class)
  public ResponseEntity<Object> exception(SpendeskException exception) {
    return new ResponseEntity<>(
        new ApiError(exception.getMessage(), exception.getCode(), exception.getHttpStatus()),
        exception.getHttpStatus());
  }
}
