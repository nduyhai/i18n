package com.nduyhai.i18n;

import com.nduyhai.i18n.core.AbstractBusinessException;
import com.nduyhai.i18n.core.BaseException;
import com.nduyhai.i18n.core.BaseHttpErrorCode;
import java.util.Locale;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@EnableConfigurationProperties(I18nProperties.class)
@ConditionalOnProperty(prefix = "i18n", name = "enabled", havingValue = "true", matchIfMissing = true)
@RestControllerAdvice
public class I18nAutoConfiguration {

  private final MessageSource messageSource;

  public I18nAutoConfiguration(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(AbstractBusinessException.class)
  public ResponseEntity<ProblemDetail> handleBaseException(AbstractBusinessException ex,
      Locale locale) {

    HttpStatusCode status = status(ex);

    String localizedMessage = messageSource.getMessage(ex.getErrorCode().getMessageKey(), null,
        locale);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, localizedMessage);
    problemDetail.setProperty("error_code", ex.getErrorCode().getCode());

    return ResponseEntity.status(status)
        .body(ProblemDetail.forStatusAndDetail(status, problemDetail.getDetail()));

  }


  private HttpStatusCode status(BaseException ex) {
    if (ex instanceof BaseHttpErrorCode) {
      return ((BaseHttpErrorCode) ex).getHttpStatusCode();
    }
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }


}
