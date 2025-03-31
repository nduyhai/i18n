# i18n

Try to use i18n in your project.

## Features

- Support multiple languages
- Support error message with I18n
- Support message key with Redis, or file, or cache

## Usage
### Using local yml file

```bash
    <dependency>
      <groupId>com.nduyhai</groupId>
      <artifactId>i18n-spring-boot-starter</artifactId>
      <version>0.0.7</version>
    </dependency>
```

### Using Redis

```bash
 <dependency>
      <groupId>com.nduyhai</groupId>
      <artifactId>i18n-redis-spring-boot-starter</artifactId>
      <version>0.0.7</version>
    </dependency>

```
### Using Abstract cache

```bash
  <dependency>
      <groupId>com.nduyhai</groupId>
      <artifactId>i18n-cache-spring-boot-starter</artifactId>
      <version>0.0.7</version>
    </dependency>
```

## Config ```settings.xml```

Since GitHub Packages require authentication, it's best to configure it in your Maven settings.xml
file.

Open or create the file:

Global: ~/.m2/settings.xml (for all projects)
Project-specific: <project-root>/settings.xml
Add your GitHub credentials:

```xml

<servers>
  <server>
    <id>github</id>
    <username>YOUR_GITHUB_USERNAME</username>
    <password>YOUR_PERSONAL_ACCESS_TOKEN</password>
  </server>
</servers>

```

Replace YOUR_GITHUB_USERNAME with your GitHub username.
Replace YOUR_PERSONAL_ACCESS_TOKEN with a GitHub personal access token (PAT).
It must have the read:packages scope.

