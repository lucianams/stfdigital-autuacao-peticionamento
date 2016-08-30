FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD app-lib-layer/BOOT-INF/lib/ /app/BOOT-INF/lib/
ADD app-lib-layer/org/ /app/org/
ADD app-additional-layer/BOOT-INF/lib /app/BOOT-INF/lib/
ADD app-layer/BOOT-INF/classes/ /app/BOOT-INF/classes/
ADD app-layer/META-INF/ /app/META-INF/
COPY docker-entrypoint.sh /docker-entrypoint.sh
ADD truststore.jks truststores/truststore.jks
ENTRYPOINT ["/docker-entrypoint.sh"]
