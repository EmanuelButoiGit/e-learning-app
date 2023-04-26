# FROM openjdk:17-jdk-alpine
# VOLUME /tmp
# ARG JAR_FILE
# COPY ${JAR_FILE} media-service.jar
# ENTRYPOINT ["java", "-jar", "/media-service.jar"]

# FROM openjdk:17-jdk-alpine
# VOLUME /tmp
# WORKDIR /media-service
# COPY build/libs/media-service-0.0.1-SNAPSHOT.jar media-service.jar
# ENTRYPOINT ["java", "-jar", "media-service.jar"]
#
# # Run bootJar task to generate executable jar file
# RUN ./gradlew bootJar
# # Copy the generated jar file into the container
# COPY build/libs/media-service-0.0.1-SNAPSHOT.jar media-service.jar

FROM openjdk:17-jdk-alpine

# Set working directory to /media-service
WORKDIR /media-service

# Copy Gradle files
COPY build.gradle .
COPY gradlew .
COPY gradle gradle

# Download dependencies
RUN ./gradlew dependencies

# Copy source code
COPY src/main src/main
COPY src/test src/test

# Build executable jar file
RUN ./gradlew bootJar

# Copy executable jar file into container
COPY build/libs/media-service-0.0.1-SNAPSHOT.jar media-service.jar

# Set command to run when container starts
ENTRYPOINT ["java", "-jar", "media-service.jar"]