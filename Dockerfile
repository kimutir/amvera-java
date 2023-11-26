FROM maven:3.9.4 as build
#EXPOSE 8080
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package

FROM openjdk:11
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]