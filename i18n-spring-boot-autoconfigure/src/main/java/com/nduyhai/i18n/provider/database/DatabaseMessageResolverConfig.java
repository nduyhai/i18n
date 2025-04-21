package com.nduyhai.i18n.provider.database;


import com.nduyhai.i18n.I18nProperties;
import com.nduyhai.i18n.core.resolver.I18nMessageResolver;
import com.nduyhai.i18n.core.resolver.database.DatabaseMessageResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(I18nProperties.Database.class)
@ConditionalOnProperty(prefix = "i18n", name = "provider", havingValue = "DATABASE")
public class DatabaseMessageResolverConfig {

    public static final String I18N_DATA_SOURCE = "i18nDataSource";

    @Bean
    @ConditionalOnMissingBean(name = I18N_DATA_SOURCE)
    public DataSource i18nDataSource(DataSource dataSourceDefault) {
        return dataSourceDefault;
    }

    @Bean
    @ConditionalOnMissingBean(name = "i18nJdbcTemplate")
    public JdbcTemplate i18nJdbcTemplate(@Qualifier(I18N_DATA_SOURCE) DataSource i18nDataSource) {
        return new JdbcTemplate(i18nDataSource);
    }

    @Bean
    @ConditionalOnMissingBean(name = "i18nTransactionManager")
    public PlatformTransactionManager i18nTransactionManager(@Qualifier(I18N_DATA_SOURCE) DataSource i18nDataSource) {
        return new DataSourceTransactionManager(i18nDataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public I18nMessageResolver i18nMessageResolver(
            @Qualifier("i18nJdbcTemplate") JdbcTemplate jdbcTemplate,
            @Qualifier("i18nTransactionManager") PlatformTransactionManager transactionManager) {
        return new DatabaseMessageResolver(jdbcTemplate, transactionManager);
    }
}
