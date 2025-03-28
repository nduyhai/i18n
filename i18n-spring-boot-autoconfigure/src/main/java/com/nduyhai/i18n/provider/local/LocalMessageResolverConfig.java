package com.nduyhai.i18n.provider.local;

import com.nduyhai.i18n.core.resolver.I18nMessageResolver;
import com.nduyhai.i18n.core.resolver.LocalI18nMessageResolver;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

@ImportAutoConfiguration(MessageResourceConfig.class)
@ConditionalOnProperty(prefix = "i18n", name = "provider", havingValue = "LOCAL", matchIfMissing = true)
public class LocalMessageResolverConfig {

  @Bean
  @ConditionalOnMissingBean
  public I18nMessageResolver i18nMessageResolver(MessageSource i18nMessageSource) {
    return new LocalI18nMessageResolver(i18nMessageSource);
  }

}
