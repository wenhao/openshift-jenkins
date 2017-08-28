#!/bin/bash -eu

cat <<EOF> ~/.kube/config
apiVersion: v1
clusters:
- cluster:
    insecure-skip-tls-verify: true
    server: https://${OPENSHIFT_HOST}
  name: ${OPENSHIFT_HOST}
contexts:
- context:
    cluster: ${OPENSHIFT_HOST}
    namespace: ${OPENSHIFT_NAMESPACE}
    user: ${OPENSHIFT_USER}/${OPENSHIFT_HOST}
  name: ${OPENSHIFT_NAMESPACE}/${OPENSHIFT_HOST}/${OPENSHIFT_USER}
current-context: ${OPENSHIFT_NAMESPACE}/${OPENSHIFT_HOST}/${OPENSHIFT_USER}
kind: Config
preferences: {}
users:
- name: ${OPENSHIFT_USER}/${OPENSHIFT_HOST}
  user:
    token: ${OPENSHIFT_TOKEN}
EOF
