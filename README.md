# openshift-jenkins


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
