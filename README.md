# i18n Spring Boot Starter

Pluggable i18n error message resolution for Spring Boot. Throws a domain exception → gets a localized `ProblemDetail`
response. Supports local property files, Redis, or any Spring Cache backend.

## Installation

Pick one starter depending on where you store your messages.

**Local `.properties` files (default)**

```xml

<dependency>
    <groupId>com.nduyhai</groupId>
    <artifactId>i18n-spring-boot-starter</artifactId>
    <version>0.0.7</version>
</dependency>
```

**Redis**

```xml

<dependency>
    <groupId>com.nduyhai</groupId>
    <artifactId>i18n-redis-spring-boot-starter</artifactId>
    <version>0.0.7</version>
</dependency>
```

**Spring Cache abstraction** (Caffeine, Hazelcast, etc.)

```xml

<dependency>
    <groupId>com.nduyhai</groupId>
    <artifactId>i18n-cache-spring-boot-starter</artifactId>
    <version>0.0.7</version>
</dependency>
```

## Quick Start

### 1. Define your error codes

```java
public enum GreetingErrorCode implements BaseHttpErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "G400000", "greeting.error.badRequest"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "G400001", "greeting.error.invalidRequest");

    // constructor + getCode() / getMessageKey() / getHttpStatusCode() ...
}
```

### 2. Create a domain exception

```java
public class GreetingBusinessException extends AbstractBusinessException {
    public GreetingBusinessException(BaseErrorCode code, Object... params) {
        super(code, params);
    }
}
```

### 3. Add message files

`src/main/resources/i18n/messages_en.properties`

```properties
greeting.error.badRequest=The request is invalid. Please check your input and try again.
greeting.error.invalidRequest=Invalid request: {0}
```

`src/main/resources/i18n/messages_vi.properties`

```properties
greeting.error.badRequest=Yêu cầu không hợp lệ.
greeting.error.invalidRequest=Lỗi rồi bạn ơi: {0}!
```

### 4. Throw and forget

```java
// HTTP 400 + localized message resolved from Accept-Language header
throw new GreetingBusinessException(GreetingErrorCode.INVALID_REQUEST, "name is required");
```

The built-in `@RestControllerAdvice` catches `AbstractBusinessException` and returns
a [RFC 7807 Problem Detail](https://www.rfc-editor.org/rfc/rfc7807) response:

```json
{
  "type": "about:blank",
  "title": "Invalid request: name is required",
  "status": 400,
  "detail": "Invalid request: name is required",
  "error_code": "G400001"
}
```

### 5. Configure locale resolution

```yaml
spring:
  web:
    locale: en
    locale-resolver: accept_header  # reads Accept-Language header

i18n:
  enabled: true  # true by default
```

## Redis backend

Messages are stored in Redis with the key format `{messageKey}|{languageTag}`:

```
greeting.error.badRequest|en  →  "The request is invalid."
greeting.error.badRequest|vi  →  "Yêu cầu không hợp lệ."
```

## Cache backend

Uses Spring Cache abstraction. Populate a cache named `i18n` with the same `{messageKey}|{languageTag}` key format
before the application serves requests.

## Examples

See the [`examples/`](examples/) directory for runnable Spring Boot apps:

| Example                                             | Description                                      |
|-----------------------------------------------------|--------------------------------------------------|
| [`i18n-example`](examples/i18n-example)             | Local `.properties` files                        |
| [`i18n-redis-example`](examples/i18n-redis-example) | Redis backend (run `docker-compose up -d` first) |
| [`i18n-cache-example`](examples/i18n-cache-example) | Spring Cache abstraction                         |
