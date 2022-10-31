# java-reactive-programming
> The aim of this project is to demonstrate the reactive programming using Spring Webflux and Spring Data R2DBC. 
> Also, new features like cache in memory with Redis will be included in a near future.
> 
> _Coded by [atromilen](https://github.com/atromilen)_

## About technology

**Spring WebFlux**

This reactive-stack web framework was added later in version 5.0 to support fully non-blocking Reactive Streams back 
pressure. Spring WebFlux internally uses [Projec Reactor](https://projectreactor.io/) and its publisher implementations 
[Flux](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html) and 
[Mono](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html).


**Spring Data R2DBC**

Spring Data R2DBC follows the [Reactive Relational Database Connectivity](https://r2dbc.io/) specification in order to 
integrate SQL database using reactive drivers.

**Wildfly**

This framework is used for database migration, allowing us to remodel our application's database schema in a reliably 
and easy way, having a backup for every change that our database schema suffer in the time.

**Docker utilities**

The project have a docker-compose file that starts all the services needed by this project, such as the **postgres** 
database and the postgres client **pgadmin** (web version). So you won't need to install extra software to run the app,
all is included in the box thanks to docker.

## Prerequisites
1. Java (jdk11) the base language used to code this application. You can install it with sdkman! in *nix
2. [Docker](https://docs.docker.com/get-docker/) in order to start up the app's related services with docker-compose.

## Getting Started
...

