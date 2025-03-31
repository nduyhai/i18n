package com.nduyhai.i18n.core.resolver;

import com.nduyhai.i18n.core.error.AbstractBusinessException;
import java.util.Locale;

public interface MessageKeyResolver {

  String resolve(AbstractBusinessException ex, Locale locale);
}
