FROM maven:3.8.6-eclipse-temurin-11-alpine
RUN mkdir file-store
RUN chmod 755 file-store
WORKDIR file-store
COPY src src
COPY pom.xml pom.xml
RUN mvn clean package install -DskipTests
EXPOSE 8081
ENTRYPOINT ["java","-jar","target/file-store-server-0.0.1-SNAPSHOT.jar","--files.basePath=/tmp/file-store/files"]