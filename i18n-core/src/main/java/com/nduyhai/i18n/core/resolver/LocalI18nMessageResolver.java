package com.nduyhai.i18n.core.resolver;

import com.nduyhai.i18n.core.error.AbstractBusinessException;
import java.util.Locale;
import org.springframework.context.MessageSource;

public class LocalI18nMessageResolver implements I18nMessageResolver {

  private final MessageSource messageSource;

  public LocalI18nMessageResolver(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public String resolveMessage(Locale locale, AbstractBusinessException exception) {
    return messageSource.getMessage(
        exception.getErrorCode().getMessageKey(),
        exception.getParametersArray(),
        locale
    );
  }
} 