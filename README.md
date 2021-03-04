# Backend
Maven/Spring Boot project for backend application. It implements REST API for data managing (CRUD).
- Open JDK 1.8
- Maven 3.3.9

All maven dependencies are configured in `pom.xml` file.
All configuration properties are stored in `application.properties` file.

## Database creation
We are using MySQL 5.7 database server for data storage.
To create database schema and set privileges we need to execute SQL code stored in script `/src/main/resources/scripts/create_database.sql`
To execute create database schema we can use MySQL Workbench or run command from MySQL console.

## Table creation
When database is created and privileges are configured we can create tables structure.
To manage tables creation and to trace database version we are using Flyway framework. All Flyway scripts are stored in folder `/src/main/resources/flyway/migrations/` with correct order.
To run flyway migration please run command: `mvn flyway:migrate`
To check database status run command: `mvn flyway:info`
If you want to delete existing and create new tables run command: `mvn flyway:clean`

## Build
Run `mvn clean install -DskipTests`

## Running unit tests
Run `mvn clean install`

## Start Spring Boot Application
Run using `mvn spring-boot:run` 

## REST API Documentation
REST API is documented using Swagger. Documentation is available on address http://localhost:8080/boot-camp-project/swagger-ui.html

# Frontend 
To test backend and frontend we need to start Angular frontend application as well.

Run `ng serve` in root folder of Angular project.
Angular application will be available on address http://localhost:4200

Create new user on Sign Up page http://localhost:4200/signup

## Application Manual

Application enables registration and login of user with three different roles (admin, moderator and user).
We can register user on sign up page. In this case user will get default role `ROLE_USER`.
User with admin and moderator role can be created through Postman. In this case roles have next formats:
- admin
- moderator
- user

Example of request body is listed in API (Swagger).

As a user with `ROLE_ADMIN` we can create, edit and delete employees. All employees are shown in table.
As a user with `ROLE_USER` we can search for employee by email.
