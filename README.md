# E_stack Project

## Contexte du Projet
En tant que développeur full stack freelance, vous êtes chargé de créer un site web pour la communauté des apprenants de l'école Enaa, similaire à Stack Overflow. L'objectif est de fournir aux étudiants un espace où ils peuvent partager des problèmes académiques et trouver des solutions collaboratives. Ce site offrira une interface conviviale pour poser des questions, répondre aux questions des autres, et favoriser l'apprentissage mutuel.

Cette plateforme collaborative en ligne permet aux utilisateurs de poser des questions, d’apporter des réponses, de voter pour les contenus, et de télécharger des fichiers pour enrichir les discussions. Elle inclut un système d'authentification, un mécanisme de votes, et des fonctionnalités de gestion des utilisateurs.

## Fonctionnalités Clés
- **Gestion des utilisateurs :** Inscription, connexion, déconnexion et gestion des rôles (apprenants, administrateurs).
- **Poser des questions :** Les utilisateurs peuvent poser des questions sur des problèmes académiques.
- **Répondre aux questions :** Les utilisateurs peuvent répondre aux questions des autres utilisateurs.
- **Système de votes :** Possibilité de voter pour les meilleures questions et réponses.
- **Modération :** Les administrateurs peuvent modérer le contenu et gérer les utilisateurs.

## Table of Contents
- [Setup](#setup)
- [Endpoints](#endpoints)
  - [User Endpoints](#user-endpoints)
  - [Question Endpoints](#question-endpoints)
  - [Answer Endpoints](#answer-endpoints)
  - [Vote Endpoints](#vote-endpoints)
  - [Image Upload Endpoints](#image-upload-endpoints)
  - [Authentication Endpoints](#authentication-endpoints)
- [Running the Application](#running-the-application)

## Prerequisites
- Node.js and npm (for Angular)
- Java 8 or higher
- Maven
- Postgres database
- test Mock
- jenkins file
- docker file

## Setup

### Frontend (Angular)
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Run the development server:
   ```bash
   ng serve
   ```
   The app will be accessible at `http://localhost:4200/`.

### Backend (Spring Boot)
1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Set up your MySQL database and update the `application.properties` file with the database configuration.
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the Spring Boot application:
   ```bash
   java -jar target/stackoverflow-clone.jar
   ```
   The backend will be accessible at `http://localhost:8080/`.

## Endpoints

### User Endpoints
- **POST /signup** - Register a new user.
- **POST /auth** - Login and obtain JWT token.

### Question Endpoints
- **POST /api/question** - Create a new question (requires authentication).
- **GET /api/questions/{pageNumber}** - Get all questions with pagination.
- **GET /api/question/{userId}/{questionId}** - Get a question by ID with answers.
- **GET /api/questions/{userId}/{pageNumber}** - Get questions by user ID.
- **DELETE /api/question/{questionId}** - Deletes a question by its ID.

### Answer Endpoints
- **POST /api/answer** - Create a new answer for a question (requires authentication).
- **GET /api/answer/{answerId}** - Approve an answer.
- **PUT /api/answer/{id}** - Edits an existing answer.
- **DELETE /api/answer/{id}** - Deletes an answer by its ID.

### Vote Endpoints
- **POST /api/vote** - Add vote to a question (requires authentication).
- **POST /api/answer/vote** - Add a vote to an answer.

### Image Upload Endpoints
- **POST /api/image/upload** - Upload an image into the database (requires authentication).

### Authentication Endpoints
- **POST /api/auth/register-admin** - Registers a new admin user.
- **POST /api/auth/login** - Authenticates a user.

## Running the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/E_stack.git
   ```
2. Install the required dependencies using Maven:
   ```bash
   mvn clean install
   ```
3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   The application will be available at `http://localhost:8080/api`.
