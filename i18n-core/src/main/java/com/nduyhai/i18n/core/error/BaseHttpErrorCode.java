package com.nduyhai.i18n.core.error;

import org.springframework.http.HttpStatusCode;

public interface BaseHttpErrorCode extends BaseErrorCode {

  HttpStatusCode getHttpStatusCode();

}
