package com.nduyhai.i18n.example;

import java.util.Objects;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class I18nCacheExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(I18nCacheExampleApplication.class, args);
  }


  @Bean
  public CommandLineRunner commandLineRunner(CacheManager cacheManager) {
    return args -> {
      Cache i18n = cacheManager.getCache("i18n");
      if (Objects.nonNull(i18n)) {
        i18n.put(GreetingErrorCode.INTERNAL_SERVER_ERROR.getMessageKey() + "|vi",
            "Đã xảy ra lỗi nội bộ");
        i18n.put(GreetingErrorCode.INTERNAL_SERVER_ERROR.getMessageKey() + "|en", "Error occurred");

        i18n.put(GreetingErrorCode.BAD_REQUEST.getMessageKey() + "|vi", "Đã xảy ra lỗi yêu cầu");
        i18n.put(GreetingErrorCode.BAD_REQUEST.getMessageKey() + "|en", "The request is invalid");

        i18n.put(GreetingErrorCode.INVALID_REQUEST.getMessageKey() + "|vi", "Yêu cầu không hợp lệ");
        i18n.put(GreetingErrorCode.INVALID_REQUEST.getMessageKey() + "|en",
            "The request is invalid");

      }
    };
  }

}
