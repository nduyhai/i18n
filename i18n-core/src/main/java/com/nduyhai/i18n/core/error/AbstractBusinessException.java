package com.nduyhai.i18n.core.error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractBusinessException extends RuntimeException implements BaseException {

  protected final transient BaseErrorCode errorCode;
  protected final transient List<Object> parameters;

  protected AbstractBusinessException(BaseErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
    this.parameters = Collections.emptyList();
  }

  protected AbstractBusinessException(BaseErrorCode errorCode, Object... parameters) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
    this.parameters = parameters == null ? Collections.emptyList() : Arrays.asList(parameters);
  }

  protected AbstractBusinessException(BaseErrorCode errorCode, List<Object> parameters) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
    this.parameters = parameters == null ? Collections.emptyList() : new ArrayList<>(parameters);
  }

  @Override
  public BaseErrorCode getErrorCode() {
    return errorCode;
  }

  public Object[] getParametersArray() {
    return parameters.toArray();
  }

  public List<Object> getParameters() {
    return parameters;
  }

}