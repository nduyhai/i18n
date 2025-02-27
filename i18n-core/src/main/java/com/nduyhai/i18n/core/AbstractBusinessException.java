package com.nduyhai.i18n.core;

public abstract class AbstractBusinessException extends RuntimeException implements BaseException {

  protected final BaseErrorCode errorCode;

  protected AbstractBusinessException(BaseErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
  }

  @Override
  public BaseErrorCode getErrorCode() {
    return errorCode;
  }

}