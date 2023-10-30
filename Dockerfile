FROM openjdk:17-alpine

ENV TZ=Asia/Shanghai LANG=C.UTF-8
ENV SERVER_PORT=80
EXPOSE 80

WORKDIR /opt
COPY ./target/*.jar ./app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
