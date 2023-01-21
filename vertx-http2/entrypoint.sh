#!/bin/sh
java -Dlog4j.configurationFile=./log4j2.xml -cp radius-onprem-3.0.0.jar:./log4j2.xml org.thangs.vertx.http2.response.jsonobject.ClientVerticleKt