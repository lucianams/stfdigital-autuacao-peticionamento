#!/bin/sh
set -e

# Aguarda o config-server subir para conseguir recuperar as configurações
echo "Aguardando o discovery na porta 8761"
while ! nc -z discovery 8761; do   
	sleep 1 # Aguarda 1s
done

exec java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -cp /app/ org.springframework.boot.loader.JarLauncher "$@"
