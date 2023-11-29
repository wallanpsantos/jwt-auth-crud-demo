#
# Build stage
#
FROM eclipse-temurin:21-alpine AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME

# Install Maven dependency packages (locally to cache them)
COPY pom.xml .
RUN ./mvnw dependency:go-offline

# Build application with Maven Wrapper
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin:21-alpine
LABEL maintainer="Projeto CRUD com JWT"
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/runner.jar"]