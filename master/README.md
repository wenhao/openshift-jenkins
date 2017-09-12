# openshift-jenkins-master

Openshift Docker Jenkins Master

## Environemnts

1. Centos7
2. OpenJDK 8
3. Jenkins 2.60.3

## Features

1. Disable users to sign up.
2. Allow anonymous read access.
3. Default user admin:admin.
4. Disable Prevent Cross Site Request Forgery exploits.
5. Skip Jenkins 2.60.3 Setup Wizard.
6. Skip Jenkins 2.60.3 Upgrade Wizard.
7. Pre-install Jenkins plugins.

## Build Image

```shell
docker build -t openshift-jenkins-master:v1.0.0 .
```

## Run

```shell
docker run -p 8080:8080 -p 50000:50000 jenkins-master:v1.0.0
```
