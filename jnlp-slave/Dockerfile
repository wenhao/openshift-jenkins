FROM centos:centos7

MAINTAINER Wen Hao (https://github.com/wenhao)

LABEL version=v1.0.0 \
      k8s.io.description="Jenkins is a continuous integration server" \
      k8s.io.display-name="Jenkins Slave 3.10" \
      openshift.io.tags="jenkins-slave"

# install java 8
RUN yum install -y wget zip unzip java-1.8.0-openjdk

# install jenkins slave
ARG HOME=/home/jenkins
ENV HOME ${HOME}

ARG VERSION=3.10

RUN curl --create-dirs -sSLo /usr/share/jenkins/slave.jar https://repo.jenkins-ci.org/public/org/jenkins-ci/main/remoting/${VERSION}/remoting-${VERSION}.jar && \
    chmod 755 /usr/share/jenkins && \
    chmod 644 /usr/share/jenkins/slave.jar

RUN mkdir /home/jenkins && \
    mkdir /home/jenkins/.jenkins && \

COPY ./scripts/jenkins-slave /usr/local/bin/jenkins-slave

RUN chmod a+x /usr/local/bin/jenkins-slave && \
    chmod a+w -R /home/jenkins

WORKDIR /home/jenkins

# install devops build tools
RUN yum install -y maven git perl

ENTRYPOINT ["jenkins-slave"]
