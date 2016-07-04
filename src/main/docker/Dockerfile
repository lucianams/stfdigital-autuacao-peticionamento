FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD app-lib-layer/lib/ /app/lib/
ADD app-additional-layer/lib /app/lib/
ADD app-layer/ /app/
COPY docker-entrypoint.sh /docker-entrypoint.sh
ADD truststore.jks truststores/truststore.jks
ENTRYPOINT ["/docker-entrypoint.sh"]
