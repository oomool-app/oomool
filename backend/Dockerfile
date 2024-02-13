FROM openjdk:17-jdk
ENV TZ Asia/Seoul
EXPOSE 8080
ADD ./build/libs/api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "/app.jar"]
