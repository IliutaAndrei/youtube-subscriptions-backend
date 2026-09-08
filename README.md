# YouTube Subscriptions Backend

A Spring Boot backend application that authenticates users with Google OAuth 2.0 and retrieves their YouTube channel subscriptions using the YouTube Data API v3.

The project was built as a practical exercise for learning OAuth 2.0, Spring Security, REST API integration, pagination, and clean backend architecture.

## Features

* Google OAuth 2.0 authentication
* Secure access to the authenticated user's YouTube data
* Retrieves all YouTube channel subscriptions
* Handles YouTube API pagination automatically
* Maps YouTube API responses to application-specific DTOs
* Separates controller, service, client, mapper, and security responsibilities

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Security OAuth2 Client
* Spring Web MVC
* `RestClient`
* YouTube Data API v3
* Maven

## Architecture

The application follows a simple layered architecture:

```text
Controller
    ↓
Service
    ↓
YouTube Client
    ↓
YouTube Data API
```

Supporting components:

```text
Security
    └── OAuth2TokenService

Mapper
    └── SubscriptionMapper

DTOs
    ├── Application DTOs
    └── YouTube API DTOs
```

### Controller

The controller exposes the REST endpoint and obtains the authenticated user's OAuth2 client.

### Service

The service contains the application logic for retrieving all subscriptions. It follows the `nextPageToken` returned by YouTube until all pages have been retrieved.

### YouTube Client

The client is responsible for communicating with the YouTube Data API using Spring's `RestClient`.

### Mapper

The mapper converts YouTube API DTOs into DTOs designed for the application's API.

### OAuth2 Token Service

The OAuth2 token service extracts the access token from the authenticated `OAuth2AuthorizedClient`.

## Authentication Flow

The application uses Google's OAuth 2.0 authorization flow.

```text
User
 ↓
Application
 ↓
Google OAuth 2.0
 ↓
User grants permissions
 ↓
Google authorization
 ↓
Spring Security
 ↓
OAuth2AuthorizedClient
 ↓
Access Token
 ↓
YouTube Data API
```

The application requests the permissions required to access the user's YouTube subscriptions.

## API Endpoint

### Get all subscriptions

```http
GET /api/subscriptions
```

The endpoint requires the user to be authenticated with Google.

Example response:

```json
[
  {
    "channelName": "channel_name",
    "subscribedAt": "2018-10-30T10:05:54.457201Z"
  },
  {
    "channelName": "channel_name",
    "subscribedAt": "2023-03-08T20:58:14.168796Z"
  }
]
```

## Pagination

The YouTube API returns subscriptions in pages.

The application automatically follows the `nextPageToken` returned by the API:

```text
Request 1
    ↓
nextPageToken
    ↓
Request 2
    ↓
nextPageToken
    ↓
...
    ↓
Last page
    ↓
All subscriptions
```

When there is no `nextPageToken`, the application stops requesting additional pages.

## Configuration

OAuth2 credentials are intentionally **not stored in the repository**.

The application expects the Google OAuth2 client configuration to be provided through environment variables / the local run configuration.

Example:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${GOOGLE_CLIENT_ID}
            client-secret: ${GOOGLE_CLIENT_SECRET}
            scope:
              - https://www.googleapis.com/auth/youtube.readonly
```

You must create your own OAuth 2.0 credentials in Google Cloud Console and configure the appropriate redirect URI for your local environment.

## Running Locally

### Prerequisites

* Java 21
* Maven
* A Google Cloud project
* YouTube Data API v3 enabled
* Google OAuth 2.0 credentials

### Start the application

Clone the repository and start the Spring Boot application:

```bash
mvn spring-boot:run
```

Alternatively, run the application directly from IntelliJ IDEA.

The application starts on:

```text
http://localhost:8080
```

After starting the application, access the subscriptions endpoint:

```text
http://localhost:8080/api/subscriptions
```

You will be redirected to Google if you are not authenticated.

## Security

Sensitive credentials such as:

* Google Client ID
* Google Client Secret
* OAuth tokens

are not committed to the repository.

The application uses Spring Security to manage authentication and OAuth2 authorization.

## Project Status

🚧 **In development**

Current functionality:

* Google authentication
* OAuth2 authorization
* YouTube API integration
* Subscription pagination
* DTO mapping
* Basic layered architecture

Planned improvements include:

* Centralized exception handling
* Better API error handling
* Token lifecycle improvements
* Frontend application
* Deployment
* Support for multiple users
* Production OAuth consent-screen configuration

## What I Learned

This project was built to gain practical experience with:

* Spring Security
* OAuth 2.0
* Google authentication
* Access tokens
* `OAuth2AuthorizedClient`
* REST API integration
* API pagination
* DTOs
* Mapping between external API models and internal models
* Layered backend architecture
* Environment variables and secret management

## License

This project is for educational and personal development purposes.
