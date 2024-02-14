FROM azul/zulu-openjdk-alpine:17
ARG SPRING_PROFILES_ACTIVE
ENV TZ Asia/Seoul
ADD ./build/libs/api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar"
, "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"
, "-Duser.timezone=Asia/Seoul"
, "/app.jar"]

