package com.nduyhai.i18n.core.resolver.cache;

import com.nduyhai.i18n.core.error.AbstractBusinessException;
import com.nduyhai.i18n.core.resolver.I18nMessageResolver;
import com.nduyhai.i18n.core.resolver.MessageKeyResolver;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class CacheMessageResolver implements I18nMessageResolver {

  private final CacheManager cacheManager;
  private final MessageKeyResolver messageKeyResolver;

  public CacheMessageResolver(CacheManager cacheManager, MessageKeyResolver messageKeyResolver) {
    this.cacheManager = cacheManager;
    this.messageKeyResolver = messageKeyResolver;
  }

  @Override
  public String resolveMessage(Locale locale, AbstractBusinessException exception) {
    Cache i18n = cacheManager.getCache("i18n");
    if (Objects.isNull(i18n)) {
      return "";
    }
    String key = messageKeyResolver.resolve(exception, locale);
    Object value = i18n.get(key, Object.class);
    return Optional.ofNullable(value).map(Object::toString).orElse("");
  }
}
