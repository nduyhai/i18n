package com.nduyhai.i18n;

import com.nduyhai.i18n.core.resolver.MessageResolverProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "i18n")
public class I18nProperties {

  private boolean enabled;
  private MessageResolverProvider provider = MessageResolverProvider.LOCAL;

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public MessageResolverProvider getProvider() {
    return provider;
  }

  public void setProvider(MessageResolverProvider provider) {
    this.provider = provider;
  }
}

