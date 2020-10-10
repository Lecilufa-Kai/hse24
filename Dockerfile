FROM adoptopenjdk/openjdk11:x86_64-debian-jre11u-nightly
RUN mkdir app
ARG JAR_FILE=target/currency-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app/currency-api.jar
ENTRYPOINT ["java","-jar","/app/currency-api.jar"]