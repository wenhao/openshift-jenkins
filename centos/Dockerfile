FROM centos:centos7

MAINTAINER Wen Hao (https://github.com/wenhao)

# Jenkins LTS packages from
# https://pkg.jenkins.io/redhat-stable/

LABEL version=v1.0.0 \
      description="Jenkins is a continuous integration server"

RUN curl https://pkg.jenkins.io/redhat-stable/jenkins.repo -o /etc/yum.repos.d/jenkins.repo && \
    rpm --import https://pkg.jenkins.io/redhat-stable/jenkins-ci.org.key && \
    yum install -y centos-release-scl-rh && \
    INSTALL_PKGS="wget tar zip unzip jenkins-2.60.2-1.1" && \
    yum -y --setopt=tsflags=nodocs install $INSTALL_PKGS && \
    rpm -V $INSTALL_PKGS && \
    yum clean all  && \
    localedef -f UTF-8 -i en_US en_US.UTF-8

ENV JAVA_VERSION 8u141
ENV BUILD_VERSION b01

RUN wget --no-cookies --no-check-certificate --header "Cookie: oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/$JAVA_VERSION-$BUILD_VERSION/jdk-$JAVA_VERSION-linux-x64.rpm" -O /tmp/jdk-8-linux-x64.rpm

RUN yum -y install /tmp/jdk-8-linux-x64.rpm

RUN alternatives --install /usr/bin/java jar /usr/java/latest/bin/java 20000
RUN alternatives --install /usr/bin/javaws javaws /usr/java/latest/bin/javaws 20000
RUN alternatives --install /usr/bin/javac javac /usr/java/latest/bin/javac 20000

ENV JAVA_HOME /usr/java/latest

# install jenkins
ARG user=jenkins
ARG group=jenkins
ARG uid=1000
ARG gid=1000
ARG http_port=8080
ARG agent_port=50000

ENV JENKINS_HOME /var/jenkins_home
ENV JENKINS_SLAVE_AGENT_PORT ${agent_port}

# Jenkins is run with user `jenkins`, uid = 1000
# If you bind mount a volume from the host or a data container,
# ensure you use the same uid
RUN groupadd -g ${gid} ${group} \
    && useradd -d "$JENKINS_HOME" -u ${uid} -g ${gid} -m -s /bin/bash ${user}

# Jenkins home directory is a volume, so configuration and build history
# can be persisted and survive image upgrades
VOLUME /var/jenkins_home

# `/usr/share/jenkins/ref/` contains all reference configuration we want
# to set on a fresh new installation. Use it to bundle additional plugins
# or config file with your custom jenkins Docker image.
RUN mkdir -p /usr/share/jenkins/ref/init.groovy.d

ENV TINI_VERSION 0.14.0
ENV TINI_SHA 6c41ec7d33e857d4779f14d9c74924cab0c7973485d2972419a3b7c7620ff5fd

# Use tini as subreaper in Docker container to adopt zombie processes
RUN curl -fsSL https://github.com/krallin/tini/releases/download/v${TINI_VERSION}/tini-static-amd64 -o /bin/tini && chmod +x /bin/tini \
  && echo "$TINI_SHA  /bin/tini" | sha256sum -c -

COPY ./scripts/init.groovy /usr/share/jenkins/ref/init.groovy.d/init.groovy

# jenkins version being bundled in this docker image
ARG JENKINS_VERSION
ENV JENKINS_VERSION ${JENKINS_VERSION:-2.60.2}

ENV JENKINS_UC http://archives.jenkins-ci.org
ENV JENKINS_UC_EXPERIMENTAL=https://updates.jenkins.io/experimental

# for main web interface:
EXPOSE ${http_port}

# will be used by attached slave agents:
EXPOSE ${agent_port}

ENV COPY_REFERENCE_FILE_LOG $JENKINS_HOME/copy_reference_file.log

COPY ./scripts/jenkins-support /usr/local/bin/jenkins-support

# from a derived Dockerfile, can use `RUN plugins.sh active.txt` to setup /usr/share/jenkins/ref/plugins from a support bundle
COPY ./scripts/install-plugins.sh /usr/local/bin/install-plugins.sh

# curl -sSL "http://username:password@myhost.com:port/pluginManager/api/xml?depth=1&xpath=/*/*/shortName|/*/*/version&wrapper=plugins" | perl -pe 's/.*?<shortName>([\w-]+).*?<version>([^<]+)()(<\/\w+>)+/\1 \2\n/g'|sed 's/ /:/'
COPY ./scripts/plugins.txt /usr/share/jenkins/ref/plugins.txt

# avoid banner
RUN echo -n ${JENKINS_VERSION:-2.60.2} > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state && \
    echo -n ${JENKINS_VERSION:-2.60.2} > /usr/share/jenkins/ref/jenkins.install.InstallUtil.lastExecVersion

COPY ./scripts/jenkins.sh /usr/local/bin/jenkins.sh

RUN chown -R ${user} "$JENKINS_HOME" /usr/share/jenkins/ref /usr/local/bin/

RUN chmod +x /usr/local/bin/install-plugins.sh /usr/local/bin/jenkins.sh && \
   /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

USER ${user}
ENTRYPOINT ["/bin/tini", "--", "/usr/local/bin/jenkins.sh"]
