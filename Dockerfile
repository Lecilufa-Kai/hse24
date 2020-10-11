FROM adoptopenjdk/openjdk11:x86_64-debian-jre11u-nightly
RUN mkdir app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app/exchange-rate-service.jar
ENTRYPOINT ["java","-jar","/app/exchange-rate-service.jar"]