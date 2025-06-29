# PowerShell script to stop all microservices

Write-Host "Stopping all services..." -ForegroundColor Red

Write-Host "Stopping business services..." -ForegroundColor Yellow
docker-compose -f docker-compose-services.yml down

Write-Host "Stopping infrastructure services..." -ForegroundColor Yellow
docker-compose -f docker-compose-infrastructure.yml down

Write-Host "Stopping Keycloak..." -ForegroundColor Yellow
docker-compose -f docker-compose-keycloak.yml down

Write-Host "Stopping Kafka..." -ForegroundColor Yellow
docker-compose -f docker-compose-kafka.yml down

Write-Host "Stopping databases..." -ForegroundColor Yellow
docker-compose -f docker-compose-databases.yml down

Write-Host "All services stopped successfully!" -ForegroundColor Green
