package com.nduyhai.i18n.core.resolver.key;

import com.nduyhai.i18n.core.error.AbstractBusinessException;
import com.nduyhai.i18n.core.resolver.MessageKeyResolver;
import java.util.Locale;

public class SimpleMessageKeyResolver implements MessageKeyResolver {

  @Override
  public String resolve(AbstractBusinessException ex, Locale locale) {
    return ex.getErrorCode().getMessageKey() + "|" + locale.getLanguage();
  }
}
