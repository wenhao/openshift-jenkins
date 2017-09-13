#！ /bin/bash -eu

OPENSHIFT_HOST_NAME=""
if[ -n "$OPENSHIFT_HOST"]; then
  OPENSHIFT_HOST_NAME="${OPENSHIFT_HOST//./-}"

  mkdir -p ~/.kube
  rm -rf ~/.kube/config
  cat > ~/.kube/config <<EOF
  apiVersion: v1
  clusters:
  - cluster:
      insecure-skip-tls-verify: true
      server: https://${OPENSHIFT_HOST}
    name: ${OPENSHIFT_HOST_NAME}
  contexts:
  - context:
      cluster: ${OPENSHIFT_HOST_NAME}
      namespace: "${OPENSHIFT_NAMESPACE}"
      user: ${OPENSHIFT_USER}/${OPENSHIFT_HOST_NAME}
    name: ${OPENSHIFT_NAMESPACE}/${OPENSHIFT_HOST_NAME}/${OPENSHIFT_USER}
  current-context: ${OPENSHIFT_NAMESPACE}/${OPENSHIFT_HOST_NAME}/${OPENSHIFT_USER}
  kind: config
  preferences: {}
  users:
  - name: ${OPENSHIFT_USER}/${OPENSHIFT_HOST_NAME}
    user:
      token: ${OPENSHIFT_TOKEN}
EOF
fi
