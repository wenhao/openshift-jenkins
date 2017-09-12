FROM wenhao/centos7-jdk8:v1.0.0

MAINTAINER Wen Hao (https://github.com/wenhao)

LABEL version=v1.0.0 \
      k8s.io.description="Jenkins is a continuous integration server" \
      k8s.io.display-name="Jenkins Slave 3.10" \
      openshift.io.tags="jenkins-slave"

# install jenkins slave
ARG HOME=/home/jenkins
ARG VERSION=3.10

ENV HOME=${HOME}

COPY ./scripts/* /usr/local/bin/

RUN curl --create-dirs -sSLo /usr/share/jenkins/slave.jar https://repo.jenkins-ci.org/public/org/jenkins-ci/main/remoting/${VERSION}/remoting-${VERSION}.jar && \
    chmod 755 /usr/share/jenkins && \
    chmod 644 /usr/share/jenkins/slave.jar && \
    mkdir -p /home/jenkins/.jenkins && \
    chmod -R +x /usr/local/bin && \
    chmod -R a+w /home/jenkins

WORKDIR ${HOME}

ENTRYPOINT ["/usr/local/bin/startup"]
