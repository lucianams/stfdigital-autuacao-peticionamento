#!/bin/sh
set -e

exec java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -cp /app/ org.springframework.boot.loader.JarLauncher "$@"
