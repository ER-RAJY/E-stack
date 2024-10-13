# E_stack Project

## Contexte du Projet

En tant que développeur full stack freelance, vous êtes chargé de créer un site web pour la communauté des apprenants de l'école **Enaa**, similaire à **Stack Overflow**. L'objectif est de fournir aux étudiants un espace où ils peuvent partager des problèmes académiques et trouver des solutions collaboratives. Ce site offrira une interface conviviale pour poser des questions, répondre aux questions des autres, et favoriser l'apprentissage mutuel.

Cette plateforme collaborative en ligne permet aux utilisateurs de poser des questions, d’apporter des réponses, de voter pour les contenus, et de télécharger des fichiers pour enrichir les discussions. Elle inclut un système d'authentification, un mécanisme de votes, et des fonctionnalités de gestion des utilisateurs.

## Fonctionnalités Clés

- **Gestion des utilisateurs** : Inscription, connexion, déconnexion et gestion des rôles (apprenants, modérateurs, administrateurs).
- **Poser des questions** : Les utilisateurs peuvent poser des questions sur des problèmes académiques.
- **Répondre aux questions** : Les utilisateurs peuvent répondre aux questions des autres utilisateurs.
- **Système de votes** : Possibilité de voter pour les meilleures questions et réponses.
- **Modération** : Les administrateurs peuvent modérer le contenu et gérer les utilisateurs.
## Table of Contents

1. [Vote Endpoints](#vote-endpoints)
2. [Question Endpoints](#question-endpoints)
3. [Image Upload Endpoints](#image-upload-endpoints)
4. [Authentication Endpoints](#authentication-endpoints)
5. [Answer Endpoints](#answer-endpoints)

## Vote Endpoints

### Add a vote to a question
- **Endpoint:** `POST /api/vote`
- **Description:** Adds a vote to a question.
- **Request Body:**
    ```json
    {
      "questionId": 1,
      "voterId": 2,
      "voteType": "UPVOTE"
    }
    ```
- **Response:** 201 Created (on success) or 400 Bad Request (on failure)

### Add a vote to an answer
- **Endpoint:** `POST /api/answer/vote`
- **Description:** Adds a vote to an answer.
- **Request Body:**
    ```json
    {
      "answerId": 1,
      "voterId": 2,
      "voteType": "UPVOTE"
    }
    ```
- **Response:** 201 Created (on success) or 400 Bad Request (on failure)

## Question Endpoints

### Post a new question
- **Endpoint:** `POST /api/question`
- **Description:** Allows users to post a new question.
- **Request Body:**
    ```json
    {
      "title": "How to implement authentication in Spring Boot?",
      "content": "Can someone explain how authentication works in Spring Boot?",
      "apprenantId": 1
    }
    ```
- **Response:** 201 Created (on success) or 400 Bad Request (on failure)

### Count all questions
- **Endpoint:** `GET /api/questions/count`
- **Description:** Returns the total number of questions posted.
- **Response:** 200 OK with the count of questions

### Get paginated list of questions
- **Endpoint:** `GET /api/questions/{pageNumber}`
- **Description:** Fetch a paginated list of all questions.
- **Path Variables:** 
    - `pageNumber`: The page number to fetch.
- **Response:** 200 OK with a list of questions.

### Get a question by ID for a specific user
- **Endpoint:** `GET /api/question/{apprenantId}/{questionId}`
- **Description:** Fetch a single question by its ID for a specific user.
- **Path Variables:**
    - `apprenantId`: ID of the user viewing the question.
    - `questionId`: ID of the question.
- **Response:** 200 OK with question details or 404 Not Found if the question doesn't exist.

### Get questions by user (apprenant) ID with pagination
- **Endpoint:** `GET /api/questions/{apprenantId}/{pageNumber}`
- **Description:** Fetch a list of questions posted by a specific user with pagination.
- **Path Variables:**
    - `apprenantId`: The ID of the user.
    - `pageNumber`: The page number.
- **Response:** 200 OK with a list of questions.

### Delete a question by ID
- **Endpoint:** `DELETE /api/question/{questionId}`
- **Description:** Deletes a question by its ID.
- **Path Variables:**
    - `questionId`: The ID of the question to delete.
- **Response:** 204 No Content on successful deletion or 404 Not Found if the question doesn't exist.

## Image Upload Endpoints

### Upload an image for an answer
- **Endpoint:** `POST /api/image/{answerId}`
- **Description:** Uploads an image for a specific answer.
- **Path Variables:**
    - `answerId`: The ID of the answer for which the image is uploaded.
- **Request Body:** Multipart form-data containing the image file.
- **Response:** 200 OK on successful upload or 500 Internal Server Error on failure.

## Authentication Endpoints

### Register an admin
- **Endpoint:** `POST /api/auth/register-admin`
- **Description:** Registers a new admin user.
- **Request Body:**
    ```json
    {
      "name": "Admin Name",
      "email": "admin@example.com",
      "password": "securepassword"
    }
    ```
- **Response:** 200 OK on success or 400 Bad Request if the admin already exists.

### Login
- **Endpoint:** `POST /api/auth/login`
- **Description:** Authenticates a user.
- **Request Body:**
    ```json
    {
      "username": "admin@example.com",
      "password": "securepassword"
    }
    ```
- **Response:** 200 OK with authentication token on success or 400 Bad Request on failure.

## Answer Endpoints

### Approve an answer
- **Endpoint:** `GET /api/answer/{answerId}`
- **Description:** Approves an answer.
- **Path Variables:**
    - `answerId`: The ID of the answer to approve.
- **Response:** 200 OK with approved answer details or 404 Not Found if the answer doesn't exist.

### Edit an answer
- **Endpoint:** `PUT /api/answer/{id}`
- **Description:** Edits an existing answer.
- **Path Variables:**
    - `id`: The ID of the answer to edit.
- **Request Body:**
    ```json
    {
      "content": "Updated answer content."
    }
    ```
- **Response:** 200 OK with the updated answer details or 404 Not Found if the answer doesn't exist.

### Delete an answer
- **Endpoint:** `DELETE /api/answer/{id}`
- **Description:** Deletes an answer by its ID.
- **Path Variables:**
    - `id`: The ID of the answer to delete.
- **Response:** 200 OK on successful deletion or 400 Bad Request if the deletion fails.

---

### Running the Application

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

4. The application will be available at `http://localhost:8080/api`.

---
