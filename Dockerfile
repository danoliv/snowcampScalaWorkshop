FROM  openjdk:8

ENV SCALA_VERSION 2.12.10
ENV SBT_VERSION 1.3.2

# Install Scala
RUN \
  curl -fsL http://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz | tar xfz - -C /root/ && \
  echo >> /root/.bashrc && \
  echo 'export PATH=~/scala-$SCALA_VERSION/bin:$PATH' >> /root/.bashrc

# Install sbt
RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion

# Install Scala.js dependencies
RUN \
  curl -sL https://deb.nodesource.com/setup_10.x | bash - && \
  apt-get install -y nodejs && \
  npm install -g jsdom source-map-support

# Install bloop
RUN \
  curl -L https://github.com/scalacenter/bloop/releases/download/v1.4.0-RC1/install.py | python && \
  echo 'export PATH=~/.bloop:$PATH' >> /root/.bashrc

# Expose workbench plugin port
EXPOSE 12345

# Define working directory
WORKDIR /root
