# openshift-jenkins

Openshift Docker Jenkins Oracle Java8

## Environemnts

1. Centos7
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
docker build -t openshift-jenkins-master:v1.0.0 .
```

## Run

```shell
docker run -p 8080:8080 -p 50000:50000 jenkins-master:v1.0.0
```

Get Container IP

```shell
docker inspect --format '{{ .NetworkSettings.IPAddress }}' <Container ID>
```

## Troubleshooting

Switch plugin site if failed to download jenkins plugins, available sites:

1. http://mirrors.jenkins-ci.org
2. http://archives.jenkins-ci.org

Also, Change `scripts/install-plugins.sh` line 73 to: `JENKINS_UC_DOWNLOAD=${JENKINS_UC_DOWNLOAD:-"$JENKINS_UC"}`

### 上传自定义docker image

1. oc cluster up --create-machinen
2. oc login -u system:admin
3. oc project default
4. oc get svc -n default | grep registry //获取internal registry IP
5. oc expose service docker-registry -n default //为internal registry添加外部访问
6. docker-machine env openshift
7. oc login -u <user_name, not system users>
8. oadm policy add-role-to-user system:registry <user_name> //授予docker push 权限
9. oadm policy add-role-to-user admin <user_name> -n openshift
10. oadm policy add-role-to-user system:image-builder <user_name>
11. oc whoami -t //获取token_value
12. docker login -u <username> -p <token_value> <registry_ip>:<port>
13. docker pull jenkins/jenkins:lts
14. docker tag jenkins/jenkins:lts <IP>:<PORT>/openshift/<image_name>
15. docker push <IP>:<PORT>/openshift/<image_name>:<version>

### 查看jenkins机器

1. oc login -u <user_name>
2. oc rsh <pod>

### 创建token

1. oc create serviceaccount <service_name>
2. oc policy add-role-to-user admin system:serviceaccount:<project_name>:<service_name>
3. oc describe serviceaccount <service_name>
4. oc describe secret <token_name>

## 获取token

1. https://<openshift_IP>:<PORT>/oauth/token/request
2. Authorization: Bearer <token>
