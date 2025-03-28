package com.nduyhai.i18n.example;

import com.nduyhai.i18n.core.error.AbstractBusinessException;
import com.nduyhai.i18n.core.error.BaseErrorCode;
import java.util.List;

public class GreetingBusinessException extends AbstractBusinessException {

  protected GreetingBusinessException(GreetingErrorCode errorCode) {
    super(errorCode);
  }

  protected GreetingBusinessException(BaseErrorCode errorCode, List<Object> parameters) {
    super(errorCode, parameters);
  }

  protected GreetingBusinessException(BaseErrorCode errorCode, Object... parameters) {
    super(errorCode, parameters);
  }
}
