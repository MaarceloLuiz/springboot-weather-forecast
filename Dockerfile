FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY README.md /app/README.md
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
ENV REPO_URL="https://raw.githubusercontent.com/MaarceloLuiz/springboot-weather-forecast/main/assets/img"
ENV WEATHER_API_KEY=${WEATHER_API_KEY}
ENV FORECAST_CITY=${FORECAST_CITY}
ENV FORECAST_DAYS=${FORECAST_DAYS}
ENV TABLE_TYPE=${TABLE_TYPE}
WORKDIR /app
COPY --from=builder /app/target/weatherforecast-0.0.1-SNAPSHOT.jar weatherforecast.jar
COPY README.md /app/README.md
ENTRYPOINT ["java", "-jar", "/app/weatherforecast.jar"]
