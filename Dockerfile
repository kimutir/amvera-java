#образ maven для сборки проекта
FROM maven:3.9.4 as build
#собираем проект в рабочую директорию
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package

#образ jdk11 для запуска собранного jar файла
FROM openjdk:11
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]