# java-reactive-programming
> The aim of this project is to demonstrate the reactive programming using Spring Webflux and Spring Data R2DBC. 
> Also, new features like cache in memory with Redis will be included in a near future.
> 
> _Coded by [atromilen](https://github.com/atromilen)_

## About technology

**Spring WebFlux**

This reactive-stack web framework was added later in version 5.0 to support fully non-blocking Reactive Streams back 
pressure. Spring WebFlux internally uses [Project Reactor](https://projectreactor.io/) and its publisher implementations 
[Flux](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html) and 
[Mono](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html).

**Spring Data R2DBC**

Spring Data R2DBC follows the [Reactive Relational Database Connectivity](https://r2dbc.io/) specification in order to 
integrate SQL database using reactive drivers.

**Wildfly**

This framework is used for database migration, allowing us to remodel our application's database schema in a reliably 
and easy way, having a backup for every change that our database schema suffer in the time. Documentation can be found
[here](https://flywaydb.org/)

**Docker compose**

The project is based on docker-compose to starts the infrastructure needed by this project. Docker compose is managing
services such as [PostgreSQL](https://www.postgresql.org/) and PostgreSQL web client [pgAdmin](https://www.pgadmin.org/).

**Makefile**

All the commands related to docker services initialization, Springboot running or gradle task were automated using
makefiles. This is a convenience and easy way to start all the infrastructure and build tasks entering only one command
instead to follow several steps to run the application. [Here](https://makefiletutorial.com/) you can find documentation
related to makefiles.

## Prerequisites
1. **Java 11** is the base language used to code this application and you will need to install the jdk 11 to run the app.
2. **Docker** is need to start up the app's related services with docker-compose. Can be found 
[here](https://docs.docker.com/get-docker/).
3. **Make** is required to run the makefile that builds and executes the application. This may be part of some Unix based
OS or can be installed through package managers such as apt-get or be part of development tools like Xcode in mac osx. 
In Windows OS you can install it through some package manager like [Scoop](https://scoop.sh/) or 
[Chocolatey](https://chocolatey.org/).

## Getting Started

1. Once you've cloned the project, it will be necessary start docker service containers (postgres SQL and pgClient) and
execute the initial database migration over _Postgre SQL_ using _Flyway_. For this, enter the following command:
    ```bash
    make build
    ```

2. To start the Spring boot application, enter:
    ```bash
    make start
    ```
    Note: always it's possible to start a Spring Boot application directly in your preferred IDE or executing _**./gradlew bootRun**_

**Optional commands**

* If you need to stop the Spring Boot Application, open another terminal window and enter:
    ```bash
    make stop
    ```

* If you want only stop (kill) the service containers but preserving the state in the containers (migrations applied, data
saved in database and so on), execute the next command:
    ```bash
    make stop-services
    ```

* If you want to stop the service containers and also you want to clean the data by removing the containers from your local
docker and erase the docker images in your local machine, execute the next command:
    ```bash
    make clean-services
    ```

### PgAdmin config
If you want to visualize the database using the web client **PgAdmin** that is provided as service container, open a
browser and go to [localhost:80](http://localhost:80), entering as credentials **admin@localhost.com**/**admin** as 
user/password respectively.

![img.png](src/main/resources/images_readme/img.png)

Next, right click on **_Servers -> register -> Server..._**. In the **General** tab, enter a name for this server 
(I entered "develop" but you can use whatever you want).

![img_1.png](src/main/resources/images_readme/img_1.png)

In the **Connection** tab, enter the next information:
- Hostname/addres: postgres
- Username: root
- Password: root

![img_2.png](src/main/resources/images_readme/img_2.png)

The connection information is provided in the file docker-compose. It's important to notice that host must be **_postgres_** 
and not localhost, due you're pointing to the host exposed in the container service called **postgres**.

As you can see, you will have created the database **_local_db_** with the schema **_movies_db_** and the 2 tables
_director_ and _movie_. You can query over these tables (remember the flyway migrations included initial example data). 

![img.png](src/main/resources/images_readme/img_3.png)
