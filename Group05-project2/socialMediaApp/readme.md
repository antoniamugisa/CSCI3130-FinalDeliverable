# Socialmediaapp

Socialmediaapp is a social networking application built using Spring Boot for the backend and React for the frontend. This project is developed as part of the CSCI3130 course group 05.

## Introduction
Socialmediaapp allows users to create accounts, manage their profiles, add friends, and interact with each other through posts and messages. It also includes administrative functionalities for managing users and their activities.

## Features
- User registration and login
- Profile management
- Adding and removing friends
- Posting and commenting
- Administrative controls
- Search and filter users and posts

## Getting Started

The SpringBoot application for the demo is present under the `backend` folder. Follow tht underline steps to start the application.

- Firstly, create a DB in your local MySQL instance. You can follow [this guide](https://dev.mysql.com/doc/mysql-getting-started/en/) to get started if you are new to this.
- Set the username, password and DB name in the `applications.properties` file present here: `backend/src/main/resources/application.properties`
- Finally, run the `backend/src/main/java/com/csci3130/assignment_2_demo/Assignment2DemoApplication.java` file.

The React application is present under the `frontend` folder. To start the front end application, execute the following steps.

- `cd frontend`
- `npm install`
- `npm start`

# !Notice! : You need at least 2 account to achieve add/remove friends feature.

### Prerequisites
- Java 17
- Maven
- MySQL
- Node.js 
- Git
- spring boot