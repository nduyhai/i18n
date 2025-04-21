package com.nduyhai.i18n;

import com.nduyhai.i18n.core.resolver.MessageResolverProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "i18n")
public class I18nProperties {

    private boolean enabled;
    private MessageResolverProvider provider;

    private Database database = new Database();

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

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public static class Database {
        private String table = "i18n_messages";
        private String codeColumn = "code";
        private String localeColumn = "locale";
        private String messageColumn = "message";

        public String getTable() {
            return table;
        }

        public void setTable(String table) {
            this.table = table;
        }

        public String getCodeColumn() {
            return codeColumn;
        }

        public void setCodeColumn(String codeColumn) {
            this.codeColumn = codeColumn;
        }

        public String getLocaleColumn() {
            return localeColumn;
        }

        public void setLocaleColumn(String localeColumn) {
            this.localeColumn = localeColumn;
        }

        public String getMessageColumn() {
            return messageColumn;
        }

        public void setMessageColumn(String messageColumn) {
            this.messageColumn = messageColumn;
        }
    }

}

