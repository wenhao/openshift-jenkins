# docker-jenkins-master-oracle-jdk8
Docker Jenkins Oracle Java8

## Features

1. Disable users to sign up.
2. Allow anonymous read access.
3. Default user admin:admin.
4. Disable Prevent Cross Site Request Forgery exploits.
5. Skip Jenkins 2.60.2 Setup Wizard.
6. Skip Jenkins 2.60.2 Upgrade Wizard.
7. Pre-install Jenkins plugins.

## Build Image

```shell
docker build jenkins-master-oracle-jdk8:v1.0.0 .
```

## Run

```shell
docker run -p 8080:8080 -p 50000:50000 jenkins-master-oracle-jdk8:v1.0.0
```

## Troubleshooting

Switch plugin site if failed to download jenkins plugins, available sites:

1. http://mirrors.jenkins-ci.org
2. http://archives.jenkins-ci.org/plugins/
