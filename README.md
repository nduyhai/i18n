# i18n

## Example

* Using en locale
```bash
curl --location --request POST 'http://localhost:8080/greeting' \
--header 'Accept-Language: en'
```

```bash
curl --location --request GET 'http://localhost:8080/greeting' \
--header 'Accept-Language: en-US'
```

* Using vn locale
```bash
curl --location --request POST 'http://localhost:8080/greeting' \
--header 'Accept-Language: vn-VN'
```

```bash
curl --location --request GET 'http://localhost:8080/greeting' \
--header 'Accept-Language: vn'
```