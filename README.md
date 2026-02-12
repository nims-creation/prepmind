# PrepMind

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue)](https://maven.apache.org/)

AI-powered learning and interview prep backend service built with Spring Boot. This application provides a 
scalable SaaS architecture for managing users, handling AI-driven content generation, and implementing an 
AI billing system to track usage and costs. Designed for developers and learners seeking personalized
AI-assisted preparation tools.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture Diagram](#architecture-diagram)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Environment Variables](#environment-variables)
- [Configuration](#configuration)
- [Testing](#testing)
- [Deployment](#deployment)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## Features

- **User Authentication & Authorization**: Secure JWT-based auth with role-based access (Student, Admin).
- **AI Content Generation**: Integration with OpenAI for generating interview prep content, learning materials, etc.
- **Usage Tracking & Billing**: Comprehensive AI usage monitoring with monthly limits, analytics, and billing support.
- **Admin Dashboard**: Analytics and usage reports for administrators.
- **Production Ready**: Designed for scalability with rate limiting, security, and robust error handling.
- **Scalable SaaS Architecture**: Microservice-ready with layered architecture, supporting high concurrency.
- **AI Billing System**: Tracks token usage, enforces quotas, and provides cost insights.

## Tech Stack

- **Java**: 21
- **Framework**: Spring Boot 3.2.4
- **Database**: PostgreSQL
- **AI Integration**: Spring AI (OpenAI)
- **Security**: Spring Security, JWT
- **Rate Limiting**: Bucket4j
- **Documentation**: OpenAPI/Swagger
- **Build Tool**: Maven
- **Other**: Lombok, Validation

## Architecture Diagram

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client Apps   │────│   Controllers   │────│    Services     │
│ (Web/Mobile)    │    │ (REST Endpoints)│    │ (Business Logic)│
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                                                        ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  Repositories   │────│   Database      │    │   AI Providers  │
│ (Data Access)   │    │ (PostgreSQL)    │    │ (OpenAI/Mock)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                        │
                                                        ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│ Rate Limiter    │    │   Security      │    │   Billing       │
│ (Bucket4j)      │    │ (JWT/Auth)      │    │   System        │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

**Architecture Overview**:
- **Layered Architecture**: Clean separation of concerns with Controllers, Services, and Repositories.
- **AI Integration**: Pluggable AI providers for flexibility and testing.
- **Security**: JWT-based authentication with role-based access control.
- **Scalability**: Stateless design, rate limiting, and database optimization for high loads.
- **Billing**: Integrated usage tracking for SaaS monetization.

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh access token

### User Management
- `POST /api/users` - Create a new user (Admin)
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/by-email` - Get user by email
- `GET /api/users` - Get all users (paginated)
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Soft delete user
- `PUT /api/users/{id}/restore` - Restore deleted user
- `GET /api/users/search` - Search users (paginated)

### AI Services
- `POST /api/v1/ai/generate` - Generate AI content
- `GET /api/v1/ai/history` - Get user's AI request history (paginated)

### Admin AI Management
- `GET /api/v1/admin/ai/usage` - Get all AI usage records (Admin only)
- `GET /api/v1/admin/ai/analytics` - Get AI analytics (Admin only)

## Getting Started

### Prerequisites
- Java 21
- Maven 3.6+
- PostgreSQL 12+

### Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd prepmind
   ```

2. Set up PostgreSQL database:
   - Create a database named `prepmind`
   - Update the database connection details in `src/main/resources/application.yaml`

3. Configure environment variables (see [Environment Variables](#environment-variables))

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

5. Access Swagger UI at `http://localhost:8080/swagger-ui.html`

## Environment Variables

The application requires the following environment variables to be set:

- `OPENAI_API_KEY`: Your OpenAI API key for AI content generation (required for AI features)
- `JWT_SECRET`: Secret key for JWT token signing (use a strong random string)
- `SPRING_DATASOURCE_URL`: JDBC URL for the PostgreSQL database (e.g., `jdbc:postgresql://localhost:5432/prepmind`)
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password

You can set these in your environment or create a `.env` file in the project root.

## Configuration

- **Database**: PostgreSQL with JPA/Hibernate
- **AI Providers**: Configurable via `AiProviderConfig` (supports OpenAI and Mock for testing)
- **Rate Limiting**: Configurable via `AiRateLimiter` (Bucket4j)
- **Security**: JWT secrets and expiration in `application.yaml`

## Testing

Run tests with:
```bash
mvn test
```

## Deployment

This application is production-ready and can be deployed to cloud platforms like AWS, Azure, or GCP. Use Docker for containerization:

```dockerfile
FROM openjdk:21-jdk
COPY target/prepmind-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

## Troubleshooting

### Common Issues

- **Database connection issues**: Ensure PostgreSQL is running and the connection details in `application.yaml` or environment variables are correct.
- **AI provider errors**: Verify the OpenAI API key is set and has sufficient credits. Check the API key validity.
- **Rate limiting**: If you encounter 429 errors, wait for the rate limit to reset or contact support for higher limits.
- **JWT authentication**: Ensure the JWT secret is set and consistent across deployments. Check token expiration.
- **Port conflicts**: The application runs on port 8080 by default. Change in `application.yaml` if needed.

### Logs

Check application logs for detailed error messages. Enable debug logging by setting `logging.level.com.nimscreation.prepmind=DEBUG` in `application.yaml`.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.