FROM maven:3.8.2-jdk-11 AS MAVEN-BUILD
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:11.0.4-jre-slim
COPY --from=MAVEN-BUILD /app/target/*-0.0.1-SNAPSHOT.jar /app.jar
CMD java -jar app.jar