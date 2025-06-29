# Docker Compose Management Script for Spring Boot Microservices

## Overview
This project is split into multiple Docker Compose files for better organization and management:

- `docker-compose-keycloak.yml` - Authentication service
- `docker-compose-infrastructure.yml` - Config Server, Eureka Discovery, and Gateway
- `docker-compose-databases.yml` - PostgreSQL, MongoDB, and Mongo Express
- `docker-compose-kafka.yml` - Kafka message broker with Zookeeper and Kafka UI
- `docker-compose-services.yml` - Your business microservices and frontend

## Network Setup
First, create the shared network:
```powershell
docker network create microservices-net
```

## Starting Services

### Option 1: Start all services at once
```powershell
docker-compose -f docker-compose-databases.yml -f docker-compose-kafka.yml -f docker-compose-keycloak.yml -f docker-compose-infrastructure.yml -f docker-compose-services.yml up -d
```

### Option 2: Start services in order (recommended)
```powershell
# 1. Start databases first
docker-compose -f docker-compose-databases.yml up -d

# 2. Start Kafka
docker-compose -f docker-compose-kafka.yml up -d

# 3. Start Keycloak
docker-compose -f docker-compose-keycloak.yml up -d

# 4. Start infrastructure services (Config Server, Eureka, Gateway)
docker-compose -f docker-compose-infrastructure.yml up -d

# 5. Start business services
docker-compose -f docker-compose-services.yml up -d
```

## Stopping Services

### Stop all services
```powershell
docker-compose -f docker-compose-services.yml -f docker-compose-infrastructure.yml -f docker-compose-keycloak.yml -f docker-compose-kafka.yml -f docker-compose-databases.yml down
```

### Stop specific service groups
```powershell
# Stop only business services
docker-compose -f docker-compose-services.yml down

# Stop infrastructure
docker-compose -f docker-compose-infrastructure.yml down

# Stop databases
docker-compose -f docker-compose-databases.yml down
```

## Service URLs
- **Keycloak Admin Console**: http://localhost:8080 (admin/admin)
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8222
- **Config Server**: http://localhost:8888
- **MongoDB Express**: http://localhost:8081
- **Kafka UI**: http://localhost:8082
- **Frontend**: http://localhost:4200
- **PostgreSQL**: localhost:5432
- **MongoDB**: localhost:27017
- **Kafka**: localhost:9092

## Service Ports
- **User Service**: 8090
- **Terrain Service**: 8091
- **Reservation Service**: 8096

## Volume Management
```powershell
# View volumes
docker volume ls

# Remove all volumes (WARNING: This will delete all data)
docker volume prune
```

## Troubleshooting
- Check logs for specific service group: `docker-compose -f <compose-file> logs -f`
- Check individual container logs: `docker logs <container-name>`
- Restart specific service group: `docker-compose -f <compose-file> restart`
