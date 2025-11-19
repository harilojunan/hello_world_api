# Hello API Service

A simple Java Spring Boot HTTP API that greets users based on the first letter of their name. This project demonstrates clean backend code, proper validation, and unit testing.

---

## 📦 Project Structure
```bash
src
├─ main
│ ├─ java
│ │ └─ com.example.hello.controller
│ │ └─ ApiController.java
│ │ └─ com.example.hello.dto
│ │ └─ ApiErrorResponse.java
│ │ └─ ApiMessageResponse.java
│ └─ resources
│ └─ application.properties
└─ test
└─ java
└─ com.example.hello.controller
└─ ApiControllerTest.java
```

**Responses:**

| Input Condition | HTTP Status | Response Body |
|-----------------|-------------|---------------|
| First letter A–M / a–m | 200 OK | `{ "message": "Hello <Name>" }` |
| First letter N–Z / n–z | 400 Bad Request | `{ "message": "Invalid Input" }` |
| Missing or empty name | 400 Bad Request | `{ "message": "Invalid Input" }` |
| Non-letter first character | 400 Bad Request | `{ "message": "Invalid Input" }` |

**Example:**

```bash
The API will start at: http://localhost:8080/api/v1/hello-api/hello-world

GET /api/v1/hello-api/hello-world?name=Hari
Response: 200 OK
{ "message": "Hello Hari" }

GET /api/v1/hello-api/hello-world?name=Roshan
Response: 400 Bad Request
{ "message": "Invalid Input" }

🚀 How to Run
1. Clone the repository
git clone <repository-url>
cd hello-api-service

