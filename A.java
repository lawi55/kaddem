//# Use OpenJDK 17 as the base image
FROM openjdk:17
//# Expose port 8082 for the application to allow external access
EXPOSE 8082
//# Set Nexus URL and JAR file version as environment variables
ENV NEXUS_URL="http://192.168.33.10:8081"
ARG VERSION=0.0.1  # Update this if your version changes
ARG GROUP_ID="tn.esprit.spring"
ARG ARTIFACT_ID="Kaddem"

//# Construct the path to the JAR file in Nexus using the version, group, and artifact ID
ENV JAR_FILE_PATH="repository/maven-releases/$(echo $GROUP_ID | sed 's/\\./\\//g')/${ARTIFACT_ID}/${VERSION}/${ARTIFACT_ID}-${VERSION}.jar"

//# Download the JAR file from Nexus using curl
RUN curl -o application.jar "${NEXUS_URL}/${JAR_FILE_PATH}"
//# Command to run the Spring Boot application using the downloaded JAR
ENTRYPOINT ["java", "-jar", "application.jar"]
