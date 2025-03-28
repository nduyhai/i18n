package com.nduyhai.i18n.provider.redis;

import com.nduyhai.i18n.core.resolver.I18nMessageResolver;
import com.nduyhai.i18n.core.resolver.redis.RedisI18nMessageResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

@AutoConfiguration(after = RedisAutoConfiguration.class)
@ConditionalOnProperty(prefix = "i18n", name = "provider", havingValue = "REDIS")
public class RedisMessageResolverConfig {

  @Bean
  @ConditionalOnMissingBean
  public I18nMessageResolver i18nMessageResolver(
      StringRedisTemplate redisTemplate) {
    return new RedisI18nMessageResolver(redisTemplate);
  }
}
