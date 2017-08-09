FROM centos:centos7

MAINTAINER Wen Hao (https://github.com/wenhao)

LABEL version=v1.0.0 \
      k8s.io.description="Jenkins is a continuous integration server" \
      k8s.io.display-name="Jenkins Slave 3.10" \
      openshift.io.expose-services="8080:http" \
      openshift.io.tags="jenkins-slave"

RUN yum install -y centos-release-scl-rh && \
    INSTALL_PKGS="wget" && \
    yum -y --setopt=tsflags=nodocs install $INSTALL_PKGS && \
    rpm -V $INSTALL_PKGS && \
    yum clean all  && \
    localedef -f UTF-8 -i en_US en_US.UTF-8

# install java 8
ENV JAVA_VERSION 8u144
ENV BUILD_VERSION b01

RUN wget --no-cookies --no-check-certificate --header "Cookie: oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/$JAVA_VERSION-$BUILD_VERSION/090f390dda5b47b9b721c7dfaa008135/jdk-$JAVA_VERSION-linux-x64.rpm" -O /tmp/jdk-8-linux-x64.rpm

RUN yum -y install /tmp/jdk-8-linux-x64.rpm

RUN alternatives --install /usr/bin/java java /usr/java/latest/bin/java 20000 && \
    alternatives --install /usr/bin/jar jar /usr/java/latest/bin/jar 20000 && \
    alternatives --install /usr/bin/javaws javaws /usr/java/latest/bin/javaws 20000 && \
    alternatives --install /usr/bin/javac javac /usr/java/latest/bin/javac 20000

ENV JAVA_HOME /usr/java/latest

# install jenkins slave
ARG user=jenkins
ENV HOME /home/jenkins
RUN groupadd -g 10000 jenkins
RUN useradd -c "Jenkins user" -d $HOME -u 10000 -g 10000 -m jenkins

ARG VERSION=3.10
ARG AGENT_WORKDIR=/home/jenkins/agent

RUN curl --create-dirs -sSLo /usr/share/jenkins/slave.jar https://repo.jenkins-ci.org/public/org/jenkins-ci/main/remoting/${VERSION}/remoting-${VERSION}.jar \
  && chmod 755 /usr/share/jenkins \
  && chmod 644 /usr/share/jenkins/slave.jar

ENV AGENT_WORKDIR=${AGENT_WORKDIR}
RUN mkdir -p /home/jenkins/.jenkins && mkdir -p ${AGENT_WORKDIR}

VOLUME /home/jenkins/.jenkins
VOLUME ${AGENT_WORKDIR}
WORKDIR /home/jenkins

COPY ./scripts/jenkins-slave /usr/local/bin/jenkins-slave
RUN chmod +x /usr/local/bin/jenkins-slave && \
    chown -R ${user} /usr/local/bin/ /home/jenkins

USER ${user}
ENTRYPOINT ["jenkins-slave"]
