FROM centos:centos7

MAINTAINER Wen Hao (https://github.com/wenhao)

# labels
LABEL description="CentOS with openjdk-8" \
      display-name="CentOS OpenJDK8" \
      tags="centos7,openjdk8,git,zip,unzip,wget,curl,subversion,openssh-client"

# Default to UTF-8 file.encoding
ENV LANG=en_US.UTF-8 \
    LC_ALL=en_US.UTF-8 \
    JAVA_VERSION=1.8.0

# install java 8 git
RUN yum update && \
    yum install -y git \
                   zip \
                   unzip \
                   java-"${JAVA_VERSION}"-openjdk \
                   wget \
                   curl \
                   subversion \
                   openssh-client \
                   && yum clean all
