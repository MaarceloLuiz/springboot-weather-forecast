FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder
WORKDIR /app
RUN mkdir -p ~/.m2

COPY ./setup.sh /app/setup.sh
RUN chmod +x /app/setup.sh && /app/setup.sh

COPY pom.xml .
COPY src ./src
COPY README.md /app/README.md
COPY README_how_to_use.md /app/README_how_to_use.md
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
ENV REPO_URL: "https://raw.githubusercontent.com/MaarceloLuiz/springboot-weather-forecast/main/assets/img"
WORKDIR /app
COPY --from=builder /app/target/weatherforecast-0.0.1-SNAPSHOT.jar weatherforecast.jar
COPY README.md /app/README.md
COPY README_how_to_use.md /app/README_how_to_use.md
ENTRYPOINT ["java", "-jar", "weatherforecast.jar"]
