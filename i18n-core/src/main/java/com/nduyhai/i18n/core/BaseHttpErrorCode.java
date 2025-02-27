package com.nduyhai.i18n.core;

import org.springframework.http.HttpStatusCode;

public interface BaseHttpErrorCode extends BaseErrorCode {

  HttpStatusCode getHttpStatusCode();

}
