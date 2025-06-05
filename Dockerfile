FROM eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /app

# Copy the built jar from target folder
COPY target/ECOMM-0.0.1-SNAPSHOT.jar /app/ECOMM-0.0.1-SNAPSHOT.jar
RUN mvn clean package -DskipTests
EXPOSE 8080
# Run the jar file
ENTRYPOINT ["java", "-jar", "ECOMM-0.0.1-SNAPSHOT.jar"]
