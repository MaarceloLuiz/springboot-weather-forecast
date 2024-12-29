FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY README.md /app/README.md
COPY README_how_to_use.md /app/README_how_to_use.md
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
ENV REPO_URL="https://raw.githubusercontent.com/MaarceloLuiz/springboot-weather-forecast/main/assets/img"
ENV WEATHER_API_KEY=${WEATHER_API_KEY}
ENV FORECAST_CITY=${FORECAST_CITY}
ENV FORECAST_DAYS=${FORECAST_DAYS}
WORKDIR /app
COPY --from=builder /app/target/weatherforecast-0.0.1-SNAPSHOT.jar weatherforecast.jar
COPY README.md /app/README.md
COPY README_how_to_use.md /app/README_how_to_use.md
ENTRYPOINT ["java", "-jar", "weatherforecast.jar"]
