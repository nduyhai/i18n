# i18n

Try to use i18n in your project.

## Features

- Support multiple languages
- Support error message with I18n
- Support message key with Redis, or file, or cache

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

