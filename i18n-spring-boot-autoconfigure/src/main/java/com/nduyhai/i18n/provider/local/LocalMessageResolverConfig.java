package com.nduyhai.i18n.provider.local;

import com.nduyhai.i18n.core.resolver.I18nMessageResolver;
import com.nduyhai.i18n.core.resolver.local.LocalI18nMessageResolver;
import com.nduyhai.i18n.provider.cache.CacheMessageResolverConfig;
import com.nduyhai.i18n.provider.redis.RedisMessageResolverConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

@ImportAutoConfiguration(MessageResourceConfig.class)
@AutoConfigureAfter(value = {CacheMessageResolverConfig.class, RedisMessageResolverConfig.class})
public class LocalMessageResolverConfig {

  @Bean
  @ConditionalOnMissingBean
  public I18nMessageResolver i18nMessageResolver(MessageSource i18nMessageSource) {
    return new LocalI18nMessageResolver(i18nMessageSource);
  }

}
