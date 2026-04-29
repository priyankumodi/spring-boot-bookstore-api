# Spring Boot Bookstore API (Learning Project)

## 🌟 Introduction
Welcome to the **Spring Boot Bookstore API**, a reference project designed to guide developers through the core concepts of building modern RESTful web services.

Rather than being a feature-complete commercial application, this project serves as a **clean-code blueprint**. It focuses on the fundamental "building blocks" that every backend engineer needs to master when moving from basic Java to the Spring Boot ecosystem.

### 🎯 Purpose
The goal of this project is to provide a clear, step-by-step example of:
- **Layered Architecture**: Understanding the separation of concerns between Controllers, Services, and Repositories.
- **Data Integrity**: Implementing JSR-303 Bean Validation to ensure high-quality data entry.
- **API Standards**: Using the DTO (Data Transfer Object) pattern to decouple internal database structures from external API responses.
- **Documentation**: Automatically generating interactive documentation with OpenAPI (Swagger) so the API is easy to explore and test.

## 🛠 Environment & Local Setup

To run this project locally, you will need to set up your development environment. Follow these steps in order:

### 1. Core Prerequisites
- **Java 21 (LTS)**: This project is built using Java 21. It is recommended to use the OpenJDK from [Adoptium (Temurin)](https://adoptium.net/).
- **Maven 3.x**: Used for dependency management and building the project. (The included `mvnw` wrapper ensures compatibility).
- **Git**: Required for version control.

### 2. Recommended IDE
- **IntelliJ IDEA**: The project is optimized for IntelliJ, providing seamless integration with Spring Boot 3.x and Maven.

### 3. Database Setup (PostgreSQL)
This project is configured to use **PostgreSQL**. You have two options for setup:

#### Option A: Manual Installation (Local)
1. Install PostgreSQL from [postgresql.org](https://www.postgresql.org/download/).
2. Create a database named `bookstore`.
3. Update `src/main/resources/application.properties` with your local `username` and `password`.

#### Option B: Docker (Recommended for Architects)
If you have **Docker** installed, you can spin up a database instantly without a manual install by running:
```bash
docker run --name bookstore-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=password -e POSTGRES_DB=bookstore -p 5432:5432 -d postgres
```

### 4. Database Visualization
**DBeaver or pgAdmin**: We recommend using a GUI client to explore the tables and verify that Hibernate has automatically generated the schema.

### 5. Testing Tools
**Swagger UI**: Access interactive documentation at http://localhost:8080/swagger-ui.html once the app is running.

**Postman**: Useful for saving complex request collections for later testing.

### Section 3: Dependency Breakdown (The "Why" Section)

Next, we should explain the `pom.xml`. Since this is a learning project, we don't want people just copying and pasting dependencies without knowing what they do.

**Here is the draft for the Dependencies section:**

## 📦 Dependencies & Their Roles

Understanding the "Why" behind each library is key to mastering Spring Boot. Here are the core dependencies used in this project:

| Dependency | Purpose |
| :--- | :--- |
| **Spring Web** | Provides the tools to build RESTful endpoints and uses Apache Tomcat as the default embedded server. |
| **Spring Data JPA** | Simplifies database interactions using Hibernate. It allows us to perform CRUD operations using Repository interfaces. |
| **PostgreSQL Driver** | The bridge that allows our Java application to communicate with the PostgreSQL database. |
| **Spring Boot Validation** | Implements JSR-303/JSR-380. Essential for using annotations like `@NotBlank` and `@Min` on our DTOs. |
| **SpringDoc OpenAPI (Swagger)** | Automatically generates the UI and JSON documentation for our API based on our Controller annotations. |

## 🏗 Project Architecture

This project follows the **Layered Architecture** pattern, a standard in Spring Boot development. This separation of concerns ensures that the code is maintainable, testable, and easy to scale.

### 1. The Controller Layer (Web)
Located in `com.artisanops.bookstore.book` and `com.artisanops.bookstore.review`.
- **Role**: Handles incoming HTTP requests and returns HTTP responses.
- **Key Responsibility**: Mapping URLs to Java methods and basic request validation.

### 2. The Service Layer (Business Logic)
Located in the `impl` sub-packages.
- **Role**: Contains the "brain" of the application.
- **Key Responsibility**: This is where business rules live (e.g., "A user cannot review a book that doesn't exist"). It orchestrates the flow between the Controller and the Repository.

> **💡 ArtisanOps Pro-Tip: Coding to Interfaces**
> Notice that we use `ReviewService` (interface) and `ReviewServiceImpl` (implementation). This is a core architectural practice. By coding to an interface, the Controller doesn't need to know *how* the service works, only *what* it does. This makes it easy to swap implementations later (e.g., switching from a local service to a cloud-based one) without changing the Controller code.

### 3. The Repository Layer (Data Access)
Interfaces extending `JpaRepository`.
- **Role**: Communicates with the PostgreSQL database.
- **Key Responsibility**: Saving, updating, and finding data using Spring Data JPA. We don't write SQL here; JPA generates it for us.

### 4. The DTO Pattern (Data Transfer Objects)
- **Concept**: We use `BookDTO` and `ReviewDTO` to talk to the outside world, while `Book` and `Review` (Entities) talk to the database.
- **Why?**: This prevents sensitive database structure details from being leaked to the API and allows us to change our database schema without breaking the API for our users.

## 🚀 Getting Started (API Roadmap)

Follow this roadmap to interact with the API and understand the data flow.

### 1. Running the Application
In IntelliJ, locate `BookstoreApplication.java` and click the **Run** icon. Alternatively, use the terminal:
```bash
./mvnw spring-boot:run
```
### 2. Exploring with Swagger UI
Open your browser and navigate to: http://localhost:8080/swagger-ui.html

### 3. Step-by-Step API Flow
#### Step A: Create a Book (POST)
- Find the POST /books endpoint in Swagger.
- Click Try it out and use a sample JSON:
```json
{
  "isbn": "978-3-16-148410-0",
  "title": "Clean Architecture",
  "description": "A craftsman's guide to software structure and design."
}
```
- **Observe**: Note the id returned in the response.
#### Step B: Add a Review (POST)
- Find POST /books/{bookId}/reviews.
- Enter the ID from Step A and use this JSON:
```json
{
  "title": "Must read",
  "content": "Essential for any aspiring architect.",
  "rating": 5,
  "reviewerName": "Artisan Learner"
}
```

#### Step C: Validation Check
- Try to create a review with a rating of 10.
- **Observe**: The API returns a 400 Bad Request, demonstrating JSR-303 Validation

## 🗺️ Next Steps (Roadmap for Improvements)

This project provides a solid foundation, but there is always more to learn! If you want to challenge yourself and make this API enterprise-ready, consider implementing these features:

1. **Global Error Handling**: Create a `@ControllerAdvice` class to handle validation errors and return a custom, clean JSON error structure instead of the default Spring Boot error page.
2. **Spring Security**: Add authentication and authorization. For example, allow anyone to **GET** books, but only an "ADMIN" to **POST** or **DELETE** them.
3. **Unit & Integration Testing**: Use **JUnit 5** and **Mockito** to write tests for your Service layer, and **MockMvc** to test your Controllers.
4. **Pagination & Sorting**: Update the `getAllBooks` endpoint to handle large datasets using Spring Data's `Pageable` interface.
5. **Database Migrations**: Implement **Flyway** or **Liquibase** to manage your database schema changes version by version.