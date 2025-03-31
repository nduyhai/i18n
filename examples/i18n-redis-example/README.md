# I18n-redis-example

## Redis set key
```bash
SET G400001|vi "This is my message"
```

## Test with API
* Using vn locale
```bash
curl --location --request PUT 'http://localhost:8081/greeting' \
--header 'Accept-Language: vi-VN'
```

