start-from-zero: run-services run-migrations
start: run-services
stop-and-delete: kill-services delete-containers-and-images
stop: kill-services

## Services to run for application startup

# Create and start service containers
run-services:
	@echo -e "${@}" && docker-compose up -d

# Execute migration scripts in database, using flyway gradle plugin
run-migrations:
	@echo -e "\n${@}" && ./gradlew flywayMigrate


## Services to run to stop the application

# Force stop service containers.
kill-services:
	@echo -e "${@}" && docker-compose kill

# Removes stopped service containers and remove associated images
delete-containers-and-images:
	@echo -e "${@}" && docker-compose rm -f && docker rmi postgres:latest dpage/pgadmin4:latest

