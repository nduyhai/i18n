package com.nduyhai.i18n.provider.redis;

import com.nduyhai.i18n.core.resolver.I18nMessageResolver;
import com.nduyhai.i18n.core.resolver.MessageKeyResolver;
import com.nduyhai.i18n.core.resolver.key.SimpleMessageKeyResolver;
import com.nduyhai.i18n.core.resolver.redis.RedisI18nMessageResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@ConditionalOnBean(RedisTemplate.class)
public class RedisMessageResolverConfig {

  @Bean
  @ConditionalOnMissingBean
  public I18nMessageResolver i18nMessageResolver(StringRedisTemplate redisTemplate,
      MessageKeyResolver messageKeyResolver) {
    return new RedisI18nMessageResolver(redisTemplate, messageKeyResolver);
  }


  @Bean
  @ConditionalOnMissingBean
  public MessageKeyResolver messageKeyResolver() {
    return new SimpleMessageKeyResolver();
  }
}
