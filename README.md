# openshift-jenkins

Openshift Docker Jenkins Oracle Java8

## Environemnts

1. Centos7/Ubuntu 16.04 LTS
2. Oracle Java 1.8 latest
3. Jenkins 2.60.2

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
docker build jenkins-master:v1.0.0 ./centos
```

## Run

```shell
docker run -p 8080:8080 -p 50000:50000 jenkins-master:v1.0.0
```

## Troubleshooting

Switch plugin site if failed to download jenkins plugins, available sites:

1. http://mirrors.jenkins-ci.org
2. http://archives.jenkins-ci.org

Also, Change `scripts/install-plugins.sh` line 73 to: `JENKINS_UC_DOWNLOAD=${JENKINS_UC_DOWNLOAD:-"$JENKINS_UC"}`
