# openshift-jenkins

1. **centos-java**: 基础镜像
2. **openshift-jenkins-master**: jenkins master镜像
3. **openshift-jenkins-jnlp-slave**: jenkins slave镜像
4. **maven**: build镜像

## openshift jenkins master

```shell
docker build -t <OCR_IP>:<5000>/<project_name>/<image_name>:<version>

docker login -u <openshift_username> -p <openshift_user_token> <OCR_IP>:<5000>

docker push <OCR_IP>:<5000>/<project_name>/<image_name>:<version>
```

## openshift jenkins jnlp slave

```shell
docker build -t <OCR_IP>:<5000>/<project_name>/<image_name>:<version>

docker login -u <openshift_username> -p <openshift_user_token> <OCR_IP>:<5000>

docker push <OCR_IP>:<5000>/<project_name>/<image_name>:<version>
```

Get Container IP

```shell
docker inspect --format '{{ .NetworkSettings.IPAddress }}' <Container ID>
```

Debug

```shell
docker run -it --entrypoint /bin/bash <image>
```

## Troubleshooting

Switch plugin site if failed to download jenkins plugins, available sites:

1. http://mirrors.jenkins-ci.org
2. http://archives.jenkins-ci.org

Also, Change `scripts/install-plugins.sh` line 73 to: `JENKINS_UC_DOWNLOAD=${JENKINS_UC_DOWNLOAD:-"$JENKINS_UC"}`

### 上传自定义docker image

1. oc cluster up --create-machine
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
14. docker tag jenkins/jenkins:lts <exposed internal registry>/openshift/<image_name>
15. docker push <exposed internal registry>/openshift/<image_name>:<version>

### 查看jenkins机器

1. oc login -u <user_name>
2. oc rsh <pod>

### 创建token

1. oc create serviceaccount <service_name>
2. oc policy add-role-to-user <role> system:serviceaccount:<project_name>:<service_name>
3. oc sa get-token <service_name>

## 获取token

1. https://<openshift_IP>:<PORT>/oauth/token/request
2. Authorization: Bearer <token>

## Openshift任意用户运行

```shell
oc edit scc restricted
```

runAsUser.Type to RunAsAny.

Ensure allowPrivilegedContainer is set to false.

## 策略

1. 每个项目拥有一套Jenkins Master和若干个Jenkins Slave。
2. Jenkins Master和Jenkins Slave的docker镜像保存在私有的docker仓库内。
3. Jenkins Master和Jenkins Slave共用一个Volume。
4. 借助Jenkins插件自动创建和回收Jenkins Slave。
5. 长时间闲置则回收Jenkins Master。
6. 项目完成之后回收所有资源（Service、Route、Volume等）。
