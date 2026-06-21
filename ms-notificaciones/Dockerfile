# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -q
COPY src/ src/
RUN ./mvnw package -DskipTests -q

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Usuario no-root por seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
COPY --from=builder /app/target/ms-notificaciones-0.0.1-SNAPSHOT.jar app.jar
RUN chown appuser:appgroup app.jar
USER appuser
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]
