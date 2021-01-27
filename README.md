# Backend
Maven/Spring Boot project for backend application. It implements REST API for data managing (CRUD).


## Database creation

We are using MySQL database for data storage.
To create database schema and set privileges we need to execute SQL code stored in script ....

## Table creation

When databace is created and privileges are configured we can create tables structure.
To manage tables creation and to trace database version we are using Flyway framework. All Flyway scripts are stored in folder .... with correct order.
To run flyway migration please run command: `mvn flyway:migrate`
To check database status run command: `mvn flyway:info`

## Build

Run `mvn clean install -DskipTests`

## Running unit tests

Run `mvn clean install`

## To test backend and frontend we need to start Angular frontend application as well.

Run `ng serve` in root folder of Angular project.