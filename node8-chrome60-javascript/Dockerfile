FROM buildpack-deps:stretch-scm

MAINTAINER Wen Hao (https://github.com/wenhao)

# labels
LABEL description="Debian with node8 & chrome" \
      display-name="Debian Node8 & Chrome" \
      tags="debian,node8,chrome"

# node8 // https://hub.docker.com/r/markadams/chromium-xvfb-js/~/dockerfile/
ENV NODE_VERSION=8.4.0-1
RUN curl -sL https://deb.nodesource.com/setup_8.x | bash - \
    && apt-get install -y nodejs=${NODE_VERSION}nodesource1~jessie1 \
    && rm -rf /var/lib/apt/lists

# chrome  // https://hub.docker.com/r/markadams/chromium-xvfb-js/~/dockerfile/