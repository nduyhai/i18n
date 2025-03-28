package com.nduyhai.i18n.core.resolver.redis;

import com.nduyhai.i18n.core.error.AbstractBusinessException;
import com.nduyhai.i18n.core.resolver.I18nMessageResolver;
import java.util.Locale;
import java.util.Optional;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisI18nMessageResolver implements I18nMessageResolver {

  private final StringRedisTemplate redisTemplate;

  public RedisI18nMessageResolver(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public String resolveMessage(Locale locale, AbstractBusinessException exception) {
    String key = exception.getErrorCode().getMessageKey() + "_" + locale.toLanguageTag();
    Object value = this.redisTemplate.opsForValue()
        .get(key);
    return Optional.ofNullable(value).map(Object::toString).orElse("");
  }
}
