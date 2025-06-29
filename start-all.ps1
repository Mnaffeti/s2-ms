# PowerShell script to start all microservices in the correct order

Write-Host "Creating shared network..." -ForegroundColor Green
docker network create microservices-net 2>$null

Write-Host "Starting databases..." -ForegroundColor Green
docker-compose -f docker-compose-databases.yml up -d

Write-Host "Starting Kafka..." -ForegroundColor Green
docker-compose -f docker-compose-kafka.yml up -d

Write-Host "Starting Keycloak..." -ForegroundColor Green
docker-compose -f docker-compose-keycloak.yml up -d

Write-Host "Waiting for services to initialize..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

Write-Host "Starting infrastructure services..." -ForegroundColor Green
docker-compose -f docker-compose-infrastructure.yml up -d

Write-Host "Waiting for infrastructure to be ready..." -ForegroundColor Yellow
Start-Sleep -Seconds 20

Write-Host "Starting business services..." -ForegroundColor Green
docker-compose -f docker-compose-services.yml up -d

Write-Host "All services started successfully!" -ForegroundColor Green
Write-Host "Access points:" -ForegroundColor Cyan
Write-Host "- Keycloak: http://localhost:8080" -ForegroundColor White
Write-Host "- Eureka: http://localhost:8761" -ForegroundColor White
Write-Host "- Gateway: http://localhost:8222" -ForegroundColor White
Write-Host "- MongoDB Express: http://localhost:8081" -ForegroundColor White
Write-Host "- Kafka UI: http://localhost:8082" -ForegroundColor White
Write-Host "- Frontend: http://localhost:4200" -ForegroundColor White
