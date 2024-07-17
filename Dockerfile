FROM khipu/openjdk17-alpine:latest AS build

LABEL org.opencontainers.image.authors = "Ahmad Zulfadli"

COPY target/mywebsite_be.jar mywebsite_be.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "mywebsite_be.jar"]
