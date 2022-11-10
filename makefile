# Some util shortcuts for local development
start: run-app
stop: stop-app
build: run-services run-migrations
stop-services: kill-services
clean-services: kill-services delete-containers-and-images

## Tasks for building container services and start Spring Boot application

# Create and start service containers
run-services:
	@echo -e "${@}" && docker-compose up -d

# Execute migration scripts in database, using flyway gradle plugin
run-migrations:
	@echo -e "\n${@}" && ./gradlew flywayMigrate

run-app:
	@echo -e "\n${@}" && ./gradlew bootRun


## Tasks for stopping application and/or service containers related

# Force stop service containers.
kill-services:
	@echo -e "${@}" && docker-compose kill

# Removes stopped service containers and remove associated images
delete-containers-and-images:
	@echo -e "${@}" && docker-compose rm -f && docker rmi postgres:latest dpage/pgadmin4:latest

stop-app:
	@echo -e "\n${@}" && ./gradlew -stop

