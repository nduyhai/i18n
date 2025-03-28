package com.nduyhai.i18n.core.resolver;

import com.nduyhai.i18n.core.error.AbstractBusinessException;
import java.util.Locale;

public interface I18nMessageResolver {

  String resolveMessage(Locale locale, AbstractBusinessException exception);
}
