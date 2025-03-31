package com.nduyhai.i18n;

import com.nduyhai.i18n.core.error.AbstractBusinessException;
import com.nduyhai.i18n.core.error.BaseException;
import com.nduyhai.i18n.core.error.BaseHttpErrorCode;
import com.nduyhai.i18n.core.resolver.I18nMessageResolver;
import com.nduyhai.i18n.provider.cache.CacheMessageResolverConfig;
import com.nduyhai.i18n.provider.local.LocalMessageResolverConfig;
import com.nduyhai.i18n.provider.redis.RedisMessageResolverConfig;
import java.util.Locale;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@EnableConfigurationProperties(I18nProperties.class)
@ImportAutoConfiguration(classes = {LocalMessageResolverConfig.class,
    RedisMessageResolverConfig.class, CacheMessageResolverConfig.class})
@ConditionalOnProperty(prefix = "i18n", name = "enabled", havingValue = "true", matchIfMissing = true)
@RestControllerAdvice
public class I18nAutoConfiguration {

  private final I18nMessageResolver i18nMessageResolver;

  public I18nAutoConfiguration(I18nMessageResolver i18nMessageResolver) {
    this.i18nMessageResolver = i18nMessageResolver;
  }

  @ExceptionHandler(AbstractBusinessException.class)
  public ResponseEntity<ProblemDetail> handleBaseException(AbstractBusinessException ex,
      Locale locale) {

    HttpStatusCode status = status(ex);

    String localizedMessage = getLocateMessage(locale, ex);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, localizedMessage);
    problemDetail.setProperty("error_code", ex.getErrorCode().getCode());
    problemDetail.setTitle(localizedMessage);

    return ResponseEntity.status(status).body(problemDetail);

  }

  private String getLocateMessage(Locale locale, AbstractBusinessException ex) {
    return this.i18nMessageResolver.resolveMessage(locale, ex);
  }

  private HttpStatusCode status(BaseException ex) {
    return ex.getErrorCode() instanceof BaseHttpErrorCode httpError ? httpError.getHttpStatusCode()
        : HttpStatus.INTERNAL_SERVER_ERROR;
  }

}
