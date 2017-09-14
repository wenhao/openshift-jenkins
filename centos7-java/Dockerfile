FROM centos:centos7

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
RUN yum update && \
    yum install -y git \
                   zip \
                   unzip \
                   wget \
                   curl \
                   subversion \
                   openssh-client && \
                   yum clean all

RUN wget --no-cookies --no-check-certificate --header "Cookie: oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/$JAVA_VERSION-$BUILD_VERSION/jdk-$JAVA_VERSION-linux-x64.rpm" -O /tmp/jdk-$JAVA_VERSION-linux-x64.rpm && \
    yum -y install /tmp/jdk-$JAVA_VERSION-linux-x64.rpm && \
    alternatives --install /usr/bin/java jar /usr/java/latest/bin/java 200000 && \
    alternatives --install /usr/bin/javaws javaws /usr/java/latest/bin/javaws 200000 && \
    alternatives --install /usr/bin/javac javac /usr/java/latest/bin/javac 200000

ENV JAVA_HOME=/usr/java/latest
