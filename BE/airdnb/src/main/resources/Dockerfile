FROM openjdk:17-jdk-slim
WORKDIR /home/app

ARG JAR_FILE=./BE/airdnb/build/libs/airdnb-0.0.1-SNAPSHOT.jar

COPY $JAR_FILE /home/app/airbnb.jar

EXPOSE 8080

ENV TZ=Asia/Seoul

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "airbnb.jar"]
