# Sanos y Salvos - Microservicios

Sistema distribuido de reportes de mascotas perdidas, construido con Spring Boot, Docker Swarm y Kafka.

## Arquitectura

```
[Cliente] → [API Gateway :8080]
               ├── ms-usuarios    :8082  (JWT, registro, login)
               ├── ms-reportes    :8081  (mascotas, reportes)
               ├── ms-coincidencias :8083 (matching de reportes)
               ├── ms-geoespacial :8084  (búsqueda por zona)
               ├── ms-integracion :8086  (orquestador)
               └── ms-notificaciones :8085 (Kafka consumer/producer)

[Service Registry - Eureka :8761]
[PostgreSQL :5432] → db_usuarios | db_reportes | db_notificaciones
[Kafka :9092] → topic: notificaciones
```

## Microservicios y Puertos

| Microservicio       | Puerto | Base de datos        |
|---------------------|--------|----------------------|
| service-registry    | 8761   | -                    |
| api-gateway         | 8080   | -                    |
| ms-usuarios         | 8082   | db_usuarios          |
| ms-reportes         | 8081   | db_reportes          |
| ms-coincidencias    | 8083   | -                    |
| ms-geoespacial      | 8084   | -                    |
| ms-notificaciones   | 8085   | db_notificaciones    |
| ms-integracion      | 8086   | -                    |

## Requisitos

- Docker Desktop con Docker Swarm habilitado
- Java 17
- Maven 3.9+

## Levantar en modo local (desarrollo)

Levantar cada microservicio en orden con Maven:

```bash
# 1. Service Registry (primero siempre)
cd service-registry && mvn spring-boot:run

# 2. Microservicios (en paralelo, en terminales separadas)
cd ms-usuarios      && mvn spring-boot:run
cd ms-reportes      && mvn spring-boot:run
cd ms-geoespacial   && mvn spring-boot:run
cd ms-coincidencias && mvn spring-boot:run
cd ms-integracion   && mvn spring-boot:run
cd ms-notificaciones && mvn spring-boot:run

# 3. API Gateway (último)
cd api-gateway && mvn spring-boot:run
```

## Despliegue con Docker Swarm

### 1. Inicializar el Swarm (solo la primera vez)

```bash
docker swarm init
```

### 2. Construir las imágenes

```bash
docker compose build
```

### 3. Desplegar el stack completo

```bash
docker stack deploy -c docker-compose.yml sanosysalvos
```

### 4. Verificar que los servicios están corriendo

```bash
docker stack services sanosysalvos
```

### 5. Ver logs de un servicio

```bash
docker service logs sanosysalvos_ms-reportes -f
```

## Añadir nodos al Swarm

### Agregar un nodo Worker

En el nodo manager, obtén el token de worker:

```bash
docker swarm join-token worker
```

Ejecutar el comando resultante en la máquina que quieres agregar como worker:

```bash
docker swarm join --token SWMTKN-1-xxxx <IP_MANAGER>:2377
```

### Agregar un nodo Manager

En el nodo manager actual:

```bash
docker swarm join-token manager
```

Ejecutar el comando resultante en la nueva máquina:

```bash
docker swarm join --token SWMTKN-1-xxxx <IP_MANAGER>:2377
```

### Ver nodos del Swarm

```bash
docker node ls
```

## Escalabilidad

Ajustar réplicas de un servicio en tiempo real:

```bash
# Escalar hacia arriba
docker service scale sanosysalvos_ms-reportes=4

# Escalar hacia abajo
docker service scale sanosysalvos_ms-reportes=1

# Verificar réplicas activas
docker service ps sanosysalvos_ms-reportes
```

## Eliminar el stack

```bash
docker stack rm sanosysalvos
```
