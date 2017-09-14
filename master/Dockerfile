FROM wenhao/centos-java:7-8u144

MAINTAINER Wen Hao (https://github.com/wenhao)

LABEL version=v1.0.0 \
      description="Jenkins is a continuous integration server" \
      display-name="Jenkins 2.60.3" \
      tags="jenkins,jenkins2,ci"

# install jenkins
ARG http_port=8080
ARG agent_port=50000
ARG JENKINS_VERSION=2.60.3
ARG HOME=/var/jenkins_home

ENV HOME=${HOME:-/var/jenkins_home} \
    JENKINS_HOME=${HOME}/jenkins \
    JENKINS_ADMIN_USERNAME=admin \
    JENKINS_ADMIN_PASSWORD=admin \
    JENKINS_SLAVE_AGENT_PORT=${agent_port} \
    JENKINS_VERSION=${JENKINS_VERSION:-2.60.3} \
    COPY_REFERENCE_FILE_LOG=${JENKINS_HOME}/copy_reference_file.log

WORKDIR ${HOME}

# `/usr/share/jenkins/ref/` contains all reference configuration we want
# to set on a fresh new installation. Use it to bundle additional plugins
# or config file with your custom jenkins Docker image.
RUN mkdir -p /usr/share/jenkins/ref/init.groovy.d

ENV TINI_VERSION v0.14.0
ENV TINI_SHA 6c41ec7d33e857d4779f14d9c74924cab0c7973485d2972419a3b7c7620ff5fd

# Use tini as subreaper in Docker container to adopt zombie processes
RUN curl -fsSL https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini-static-amd64 -o /bin/tini && chmod +x /bin/tini \
  && echo "$TINI_SHA  /bin/tini" | sha256sum -c -

COPY ./scripts/init.groovy /usr/share/jenkins/ref/init.groovy.d/init.groovy

# jenkins.war checksum, download will be validated using it
ARG JENKINS_SHA=2d71b8f87c8417f9303a73d52901a59678ee6c0eefcf7325efed6035ff39372a

# Can be used to customize where jenkins.war get downloaded from
ARG JENKINS_URL=https://repo.jenkins-ci.org/public/org/jenkins-ci/main/jenkins-war/${JENKINS_VERSION}/jenkins-war-${JENKINS_VERSION}.war

# could use ADD but this one does not check Last-Modified header neither does it allow to control checksum
# see https://github.com/docker/docker/issues/8331
RUN curl -fsSL ${JENKINS_URL} -o /usr/share/jenkins/jenkins.war \
  && echo "${JENKINS_SHA}  /usr/share/jenkins/jenkins.war" | sha256sum -c -

ENV JENKINS_UC http://archives.jenkins-ci.org
ENV JENKINS_UC_EXPERIMENTAL=https://updates.jenkins.io/experimental

# for main web interface:
EXPOSE ${http_port}

# will be used by attached slave agents:
EXPOSE ${agent_port}

COPY ./scripts/jenkins-support /usr/local/bin/jenkins-support

# from a derived Dockerfile, can use `RUN plugins.sh active.txt` to setup /usr/share/jenkins/ref/plugins from a support bundle
COPY ./scripts/install-plugins /usr/local/bin/install-plugins

# configure jenkins master before running
COPY ./scripts/configure-jenkins-master /usr/local/bin/configure-jenkins-master

# curl -sSL "http://username:password@myhost.com:port/pluginManager/api/xml?depth=1&xpath=/*/*/shortName|/*/*/version&wrapper=plugins" | perl -pe 's/.*?<shortName>([\w-]+).*?<version>([^<]+)()(<\/\w+>)+/\1 \2\n/g'|sed 's/ /:/'
COPY ./scripts/plugins.txt /usr/share/jenkins/ref/plugins.txt

# avoid banner
RUN echo -n ${JENKINS_VERSION:-2.60.3} > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state && \
    echo -n ${JENKINS_VERSION:-2.60.3} > /usr/share/jenkins/ref/jenkins.install.InstallUtil.lastExecVersion

COPY ./scripts/startup /usr/local/bin/startup

RUN chmod -R +x /usr/local/bin;sync && \
    /usr/local/bin/install-plugins < /usr/share/jenkins/ref/plugins.txt && \
    mkdir -p ${JENKINS_HOME} && \
    touch "${COPY_REFERENCE_FILE_LOG}"

ENTRYPOINT ["/bin/tini", "--", "/usr/local/bin/startup"]
