FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD peticionamento.jar app.jar
COPY docker-entrypoint.sh /docker-entrypoint.sh
ADD truststore.jks truststores/truststore.jks
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["/docker-entrypoint.sh"]
