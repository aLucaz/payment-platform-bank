# Payment Platform Bank

Bank layer of a payment platform, this repository is the resource server
that is connected to the banks services.

This server is also connected to the [Security Layer](https://github.com/aLucaz/payment-platform-security).

## Technologies and Concept used

- Oauth2 resource server
- Spring Security
    - Spring security resource server
- Client Credentials Grant Type
- Java 11
- OpenAPI 3.0 
- FeignClient
- Microservices
- Docker
- Kubernetes
- Circuit breaker
- Feign Client Fallback
- Retryable configurations
- Visa API

## Project achitecture

In progress

### Project structure

```
main
├── java
│   └── pe
│       └── client
│           └── custom
│               └── app
│                   ├── client
│                   ├── config
│                   │   └── properties
│                   ├── controller
│                   ├── dto
│                   ├── exception
│                   │   └── handler
│                   ├── model
│                   ├── service
│                   │   └── impl
│                   └── util
│                       └── constant
└── resources
    ├── static
    └── templates
```

## Project documentation

![](resources/draw-architecture.png)

## Running

![](resources/server.png)
