# I18n-example

## Set key

Change message key in `src/main/resources/i18n/**` file.

## Test with API
* Using vn locale
```bash
curl --location --request PUT 'http://localhost:8081/greeting' \
--header 'Accept-Language: vi-VN'
```

