FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY common/common-dto common/common-dto
COPY services/file-service services/file-service
RUN mvn -q install -N -DskipTests \
    && mvn -q -f common/common-dto/pom.xml install -DskipTests \
    && mvn -q -f services/file-service/pom.xml package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/services/file-service/target/file-service-*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]
