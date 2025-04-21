package com.nduyhai.i18n.core.resolver.database;

import com.nduyhai.i18n.core.error.AbstractBusinessException;
import com.nduyhai.i18n.core.resolver.I18nMessageResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Locale;

public class DatabaseMessageResolver implements I18nMessageResolver {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMessageResolver.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    public DatabaseMessageResolver(
            JdbcTemplate jdbcTemplate,
            PlatformTransactionManager transactionManager) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public String resolveMessage(Locale locale, AbstractBusinessException exception) {
        String code = exception.getErrorCode().getMessageKey();
        String defaultMessage = exception.getMessage();

        return transactionTemplate.execute(status -> {
            try {
                String query = "SELECT message FROM i18n_messages " +
                        "WHERE code = :code AND locale = :locale " +
                        "LIMIT 1";

                MapSqlParameterSource params = new MapSqlParameterSource()
                        .addValue("code", code)
                        .addValue("locale", locale.toString());

                return namedParameterJdbcTemplate.queryForObject(query, params, String.class);
            } catch (EmptyResultDataAccessException e) {
                logger.debug("No message found for code: {} and locale: {}", code, locale);
                return defaultMessage;
            } catch (DataAccessException e) {
                logger.error("Error resolving message for code: {} and locale: {}", code, locale, e);
                return defaultMessage;
            }
        });
    }
}