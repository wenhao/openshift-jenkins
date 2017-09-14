FROM centos:latest

MAINTAINER Wen Hao (https://github.com/wenhao)

# labels
LABEL description="CentOS with oracle-jdk" \
      display-name="CentOS OpenJDK8" \
      tags="centos7,oracle JDK,git,zip,unzip,wget,curl,subversion,openssh-client"

# Default to UTF-8 file.encoding
ENV LANG=en_US.UTF-8 \
    LC_ALL=en_US.UTF-8 \
    JAVA_VERSION=8u144 \
    BUILD_VERSION=b01

# install java git
RUN yum -y update && \
    yum -y install git zip unzip wget curl openssh-client && \
    wget --no-cookies --no-check-certificate --header "Cookie: oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/$JAVA_VERSION-$BUILD_VERSION/090f390dda5b47b9b721c7dfaa008135/jdk-$JAVA_VERSION-linux-x64.rpm" -O /tmp/jdk-$JAVA_VERSION-linux-x64.rpm && \
    yum -y install /tmp/jdk-$JAVA_VERSION-linux-x64.rpm && \
    yum clean all

ENV JAVA_HOME=/usr/java/latest
