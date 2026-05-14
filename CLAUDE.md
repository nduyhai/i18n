# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Test Commands

Build all modules from the root:

```bash
mvn clean install
```

Build skipping tests:

```bash
mvn clean install -DskipTests
```

Run tests for a specific module:

```bash
mvn test -pl i18n-core
mvn test -pl i18n-spring-boot-autoconfigure
```

Run a single test class:

```bash
mvn test -pl i18n-core -Dtest=YourTestClass
```

Run example apps (from their directories):

```bash
cd examples/i18n-example && mvn spring-boot:run
cd examples/i18n-redis-example && mvn spring-boot:run   # requires Redis
cd examples/i18n-cache-example && mvn spring-boot:run
```

Start Redis for local development (needed by redis and cache examples):

```bash
docker-compose up -d
```

Bump all module versions in sync (updates every `<version>` in the reactor):

```bash
mvn versions:set -DnewVersion=X.Y.Z
```

Publish library modules to Maven Central (requires GPG key and Central credentials — see below):

```bash
mvn clean deploy -P release
```

## Architecture

This is a Maven multi-module project that provides Spring Boot auto-configuration for i18n error message resolution. The
core idea: when an `AbstractBusinessException` is thrown, a `@RestControllerAdvice` catches it and resolves a localized
message using the `Accept-Language` header, returning a RFC 7807 `ProblemDetail` response.

### Module dependency graph

```
i18n-core
    └── i18n-spring-boot-autoconfigure  (depends on i18n-core)
            ├── i18n-spring-boot-starter         (local file backend)
            ├── i18n-redis-spring-boot-starter   (Redis backend)
            └── i18n-cache-spring-boot-starter   (Spring Cache abstraction backend)
```

### Core interfaces (i18n-core)

- **`BaseErrorCode`** — interface all error codes implement; has `getCode()` (machine-readable string like `"G400001"`)
  and `getMessageKey()` (Spring MessageSource key like `"greeting.error.badRequest"`).
- **`BaseHttpErrorCode extends BaseErrorCode`** — adds `getHttpStatusCode()` to map errors to HTTP status codes.
- **`AbstractBusinessException`** — base runtime exception holding a `BaseErrorCode` and optional message parameters (
  used for `{0}` placeholders in messages).
- **`I18nMessageResolver`** — single-method interface: `resolveMessage(Locale, AbstractBusinessException) → String`.
  Three implementations: `LocalI18nMessageResolver`, `RedisI18nMessageResolver`, `CacheMessageResolver`.
- **`MessageKeyResolver`** — converts an exception + locale into a cache/Redis lookup key. Default implementation (
  `SimpleMessageKeyResolver`) produces `messageKey + "|" + locale.getLanguage()` (e.g.
  `"greeting.error.badRequest|en"`).

### Auto-configuration strategy (i18n-spring-boot-autoconfigure)

`I18nAutoConfiguration` is the `@RestControllerAdvice` that handles `AbstractBusinessException`. It imports three
resolver configs with a clear priority via `@ConditionalOnMissingBean` + `@AutoConfigureAfter`:

1. **Redis** (`RedisMessageResolverConfig`) — activates when a `RedisTemplate` bean is present. Looks up messages from
   Redis using key format `messageKey|lang`.
2. **Cache** (`CacheMessageResolverConfig`) — activates when a `CacheManager` bean is present (and Redis is not). Uses
   Spring Cache abstraction; expects a cache named `"i18n"`.
3. **Local** (`LocalMessageResolverConfig`) — fallback when neither Redis nor Cache is active. Uses
   `ReloadableResourceBundleMessageSource` reading from `classpath:i18n/messages` (i.e.
   `src/main/resources/i18n/messages_en.properties`, `messages_vi.properties`, etc.).

The auto-configuration is disabled entirely with `i18n.enabled=false`.

### Implementing error codes (consumer pattern)

1. Create an enum implementing `BaseHttpErrorCode` (see `GreetingErrorCode` in examples).
2. Create a domain exception extending `AbstractBusinessException` that accepts your enum.
3. Add message keys to `src/main/resources/i18n/messages_en.properties` (and other locales).
4. Throw the exception anywhere; the advice handles the rest.

### Redis key format

For Redis and Cache backends, messages are stored/looked up with keys in the form:

```
{messageKey}|{languageTag}
```

Example: `greeting.error.badRequest|vi`

### Configuration reference

```yaml
i18n:
  enabled: true   # default: true; set false to disable the entire auto-configuration

spring:
  web:
    locale: vi_VN
    locale-resolver: accept_header   # use Accept-Language header for locale detection
```

### Publishing

Artifacts are published to **Maven Central** via the `release` Maven profile. The profile activates:

- `maven-source-plugin` — attaches `-sources.jar`
- `maven-javadoc-plugin` — attaches `-javadoc.jar` (doclint disabled)
- `maven-gpg-plugin` — signs all artifacts (uses `--pinentry-mode loopback` for CI compatibility)
- `central-publishing-maven-plugin` — uploads the signed bundle to Sonatype Central Portal and waits until fully
  published

**The three example modules are excluded** from Central publishing via
`<central.publishing.skip>true</central.publishing.skip>` in each example pom. Library modules inherit the default
`false`.

Required `~/.m2/settings.xml` configuration:

```xml

<servers>
    <server>
        <id>central</id>
        <username>YOUR_SONATYPE_TOKEN_USERNAME</username>
        <password>YOUR_SONATYPE_TOKEN_PASSWORD</password>
    </server>
</servers>
```

Token credentials are generated at [central.sonatype.com](https://central.sonatype.com) under your account → Generate
User Token.

### Spring Boot 4.0 auto-configuration package changes

Spring Boot 4.0 extracted auto-configurations from `spring-boot-autoconfigure` into per-module jars with new package
roots:

- `org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration` →
  `org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration` (in `spring-boot-data-redis`)
- `org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration` →
  `org.springframework.boot.cache.autoconfigure.CacheAutoConfiguration` (in `spring-boot-cache`)

When adding new `@AutoConfiguration(after = …)` ordering references, use the new package roots.
