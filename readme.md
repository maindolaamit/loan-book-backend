# Loan Book App

A one-stop solution for all your loan needs. This app helps you to manage your loans, track your payments and also helps
you to calculate your EMI.

## Tech Stack

- Backend: Java, Spring Boot, Hibernate
- Database: PostgresSQL

## Assumptions
PostgresSQL is used as the database which is a SQL database. The nature of the data is structured and hence a SQL database is used. 

### Installation Software required
- Docker
- Java 17
- Maven for build/test/run automation
- IntelliJ Community Edition(Optional)

### Installation Steps

#### Database
1. Install Docker on your machine
2. Clone the repository
3. Run the following [Docker Compose file](compose.yaml) to start the Database

```bash
docker-compose up
```

#### Backend - Java Spring Boot
1. Install `maven` which is a build automation tool used primarily for Java projects.
2. Navigate to the `backend` directory
3. Run the following command to start the backend server
to compile and regenerate the jar file, run the following command
```bash
mvn clean install
```
the above command will generate a jar file in the target folder, to run the jar file, run the following command

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

4. Open the browser and navigate to http://localhost:8080/api/swagger-ui.html
5. You can now see the application up and running
6. You can also test the APIs using the Swagger UI/Postman/curl


The project can be run in two modes - `local` and `prod`. The default mode is `prod`. To run the project in `prod` mode, run the following command
```bash
mvn spring-boot:run
```

The local profile will load some dummy data into the database. The prod profile will not load any data into the database.
The dummy data is loaded from the [  `loan-applications.json`  ](src/main/resources/data/loan-applications.json) file in the resources folder.