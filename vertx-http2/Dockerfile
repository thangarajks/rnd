ARG SERVICE_BASE_VERSION=v85_b05152022

FROM containers.cisco.com/dnaspaces/servicealmalinux:$SERVICE_BASE_VERSION

RUN dnf install java-1.8.0-openjdk-1:1.8.0.332.b09-2.el8_6.x86_64 --assumeyes

RUN mkdir /opt/connector && \
 mkdir /opt/connector/radius-onprem && \
 mkdir /opt/connector/radius-onprem/logs && \
 mkdir /opt/connector/radius-onprem/data && \
 chown -R dnas:dnas /opt/connector/radius-onprem


ENV LANG en_US.UTF-8
ENV LC_ALL en_US.UTF-8

WORKDIR /opt/connector/radius-onprem
#add uber jar
ADD target/vertx-http2-1.0-SNAPSHOT.jar .
ADD src/main/resources/log4j2.xml .
ADD entrypoint.sh .


WORKDIR /opt/connector/radius-onprem

ENTRYPOINT ["./entrypoint.sh"]