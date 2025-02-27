package com.nduyhai.i18n.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum DefaultErrorCode implements BaseHttpErrorCode {
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E500000",
      "error.internalServerError"),
  ;

  private final HttpStatusCode httpStatusCode;
  private final String code;
  private final String messageKey;

  DefaultErrorCode(HttpStatusCode httpStatusCode, String code, String messageKey) {
    this.httpStatusCode = httpStatusCode;
    this.code = code;
    this.messageKey = messageKey;
  }

  @Override
  public HttpStatusCode getHttpStatusCode() {
    return httpStatusCode;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessageKey() {
    return messageKey;
  }
}
