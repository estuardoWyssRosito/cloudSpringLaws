# Use the official OpenJDK 17 image as the base image
FROM azul/zulu-openjdk-alpine:17.0.6

RUN apk add --no-cache bash

# Set JAVA_OPTS for Spring profiles
ENV JAVA_OPTS="-Dspring.profiles.active=dev"

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/cloudSpringLaws-X.jar /app/cloudSpringLaws-X.jar

# Expose the port that your application will run on
EXPOSE 8083

# Specify the command to run your application
#CMD ["java", "-jar", "cloudSpringLaws-X.jar"]

# Start the app
ENTRYPOINT ["java", "-jar", "/app/cloudSpringLaws-X.jar"]