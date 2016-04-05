FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD peticionamento.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=development","-jar","/app.jar"]