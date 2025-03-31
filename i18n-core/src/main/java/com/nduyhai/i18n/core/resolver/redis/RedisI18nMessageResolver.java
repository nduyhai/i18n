package com.nduyhai.i18n.core.resolver.redis;

import com.nduyhai.i18n.core.error.AbstractBusinessException;
import com.nduyhai.i18n.core.resolver.I18nMessageResolver;
import com.nduyhai.i18n.core.resolver.MessageKeyResolver;
import java.util.Locale;
import java.util.Optional;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisI18nMessageResolver implements I18nMessageResolver {

  private final StringRedisTemplate redisTemplate;
  private final MessageKeyResolver messageKeyResolver;

  public RedisI18nMessageResolver(StringRedisTemplate redisTemplate,
      MessageKeyResolver messageKeyResolver) {
    this.redisTemplate = redisTemplate;
    this.messageKeyResolver = messageKeyResolver;
  }

  @Override
  public String resolveMessage(Locale locale, AbstractBusinessException exception) {
    String key = this.messageKeyResolver.resolve(exception, locale);
    Object value = this.redisTemplate.opsForValue()
        .get(key);
    return Optional.ofNullable(value).map(Object::toString).orElse("");
  }
}
