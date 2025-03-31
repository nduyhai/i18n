package com.nduyhai.i18n.provider.cache;

import com.nduyhai.i18n.core.resolver.I18nMessageResolver;
import com.nduyhai.i18n.core.resolver.MessageKeyResolver;
import com.nduyhai.i18n.core.resolver.cache.CacheMessageResolver;
import com.nduyhai.i18n.core.resolver.key.SimpleMessageKeyResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

@ConditionalOnBean(CacheManager.class)
public class CacheMessageResolverConfig {

  @Bean
  @ConditionalOnMissingBean
  public I18nMessageResolver i18nMessageResolver(CacheManager cacheManager,
      MessageKeyResolver messageKeyResolver) {
    return new CacheMessageResolver(cacheManager, messageKeyResolver);
  }


  @Bean
  @ConditionalOnMissingBean
  public MessageKeyResolver messageKeyResolver() {
    return new SimpleMessageKeyResolver();
  }
}
