# sanos-y-salvos-microservices


# 🐾 Sanos y Salvos — Plataforma de Recuperación de Mascotas

Sistema de microservicios para localización y recuperación de mascotas perdidas.

## Arquitectura

| Microservicio       | Puerto | Descripción                              |
|---------------------|--------|------------------------------------------|
| service-registry    | 8761   | Eureka Server (Service Discovery)        |
| api-gateway         | 8080   | Punto de entrada único (Spring Gateway)  |
| ms-reportes         | 8081   | CRUD de mascotas y reportes              |
| ms-usuarios         | 8082   | Auth JWT y gestión de perfiles           |
| ms-coincidencias    | 8083   | Motor de matching automático             |
| ms-geoespacial      | 8084   | Búsqueda por radio con PostGIS           |
| ms-notificaciones   | 8085   | Alertas vía Kafka                        |
| ms-integracion      | 8086   | Circuit Breaker con Resilience4j         |

## Requisitos previos

- Java 17
- Maven 3.8+
- PostgreSQL 15 con extensión PostGIS
- Apache Kafka 3.7+
- IntelliJ IDEA Community (recomendado)

## Instalación

### 1. Clonar el repositorio
```bash
git clone https://github.com/TU_USUARIO/sanos-y-salvos-microservices.git
cd sanos-y-salvos-microservices
```

### 2. Crear las bases de datos
Ejecuta el script `database/init.sql` en pgAdmin o psql:
```bash
psql -U postgres -f database/init.sql
```

### 3. Configurar contraseñas
En cada `application.properties`, reemplaza `TU_PASSWORD_AQUI` con tu contraseña de PostgreSQL.

### 4. Iniciar Kafka
```bash
# Zookeeper
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

# Broker
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

### 5. Iniciar microservicios (en orden)
```bash
cd service-registry && mvn spring-boot:run &
cd ms-reportes      && mvn spring-boot:run &
cd ms-usuarios      && mvn spring-boot:run &
cd ms-coincidencias && mvn spring-boot:run &
cd ms-geoespacial   && mvn spring-boot:run &
cd ms-notificaciones && mvn spring-boot:run &
cd ms-integracion   && mvn spring-boot:run &
cd api-gateway      && mvn spring-boot:run &
```

## Endpoints principales (vía API Gateway: localhost:8080)

| Método | Endpoint                                         | Descripción               |
|--------|--------------------------------------------------|---------------------------|
| POST   | /api/v1/auth/registro                            | Registrar usuario         |
| POST   | /api/v1/auth/login                               | Login → retorna JWT       |
| GET    | /api/v1/mascotas                                 | Listar mascotas           |
| POST   | /api/v1/mascotas                                 | Crear mascota             |
| POST   | /api/v1/reportes                                 | Crear reporte             |
| GET    | /api/v1/reportes/perdidos                        | Listar mascotas perdidas  |
| POST   | /api/v1/coincidencias/buscar                     | Disparar motor de match   |
| GET    | /api/v1/geo/radio?lat=X&lon=Y&radioKm=Z          | Búsqueda por radio        |
| GET    | /api/v1/notificaciones/usuario/{id}              | Ver notificaciones        |

## Monitoreo
- Eureka Dashboard: http://localhost:8761
- Prometheus:       http://localhost:9090
- Grafana:          http://localhost:3000

## Pruebas
La colección de Postman está disponible en: `postman/SanosYSalvos.postman_collection.json`
