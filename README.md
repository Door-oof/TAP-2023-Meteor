# Govetech Meteor

This project is build for the Take-Home Backend Technical Assessment for TAP 2023.

## Prerequisites
- Homebrew (Optional but recommended for easier installation of MySQL, Java and Maven)
- Apache Maven 3.8.6
- Java 11+
- MySQL
- Postman (Optional)
- Intellij IDEA

## Setup
- Clone this repository into a local directory
- Reload Maven dependencies in `pom.xml` if project does not auto-configure
- Change the MySQL password and username in `src/main/resources/application.properties` to your match your own
- Run the sql code `schema.sql` located in `src/main/resources/application.properties`

## Build

    mvnw clean install

The resulting JAR is: ./target/SpringApp-[VERSION]-SNAPSHOT.jar