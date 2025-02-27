package com.nduyhai.i18n.example;

import com.nduyhai.i18n.core.AbstractBusinessException;

public class GreetingBusinessException extends AbstractBusinessException {

  protected GreetingBusinessException(GreetingErrorCode errorCode) {
    super(errorCode);
  }
}
