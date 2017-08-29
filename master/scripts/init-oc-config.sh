#!/bin/bash -eu

OPENSHIFT_HOST_NAME=""
if [ -n "$OPENSHIFT_HOST" ]; then
  OPENSHIFT_HOST_NAME="${OPENSHIFT_HOST//./-}"

  cat <<EOF> ~/.kube/config
  apiVersion: v1
  clusters:
  - cluster:
      insecure-skip-tls-verify: true
      server: https://${OPENSHIFT_HOST}
    name: ${OPENSHIFT_HOST_NAME}
  contexts:
  - context:
      cluster: ${OPENSHIFT_HOST_NAME}
      namespace: ${OPENSHIFT_NAMESPACE}
      user: ${OPENSHIFT_USER}/${OPENSHIFT_HOST_NAME}
    name: ${OPENSHIFT_NAMESPACE}/${OPENSHIFT_HOST_NAME}/${OPENSHIFT_USER}
  current-context: ${OPENSHIFT_NAMESPACE}/${OPENSHIFT_HOST_NAME}/${OPENSHIFT_USER}
  kind: Config
  preferences: {}
  users:
  - name: ${OPENSHIFT_USER}/${OPENSHIFT_HOST_NAME}
    user:
      token: ${OPENSHIFT_TOKEN}
  EOF
fi
