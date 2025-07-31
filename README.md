# 📚 EduFleet — Backend API Service

EduFleet is an open-source backend system for managing educational fleet operations. It aims to help institutions streamline logistics, resource assignments, staff coordination, and administrative tasks through a robust and scalable web service.

This project is part of a larger vision to simplify academic and operational workflows using modern backend technologies and cloud-native deployment. Whether it's managing transport routes, staff schedules, or resource tracking, EduFleet provides a clean, RESTful API layer ready to integrate with any frontend or mobile client.

Contributions are welcome! Whether you're fixing bugs, improving performance, adding features, or just enhancing documentation—this is a collaborative effort, and every bit helps.

---

## 🧰 Tech Stack

| Layer           | Tool / Framework                     |
|----------------|---------------------------------------|
| Language        | Java 17                              |
| Framework       | Spring Boot                          |
| Database        | PostgreSQL (or configurable via `.env`) |
| Server          | Embedded Tomcat                      |
| Hosting         | AWS EC2 (Amazon Linux 2 / Ubuntu)    |
| HTTPS           | SSL via embedded keystore (PKCS12)   |
| Configuration   | `.env` + `application.yaml` with `spring.config.import` |

---

## 🚀 Deployment Overview

EduFleet is deployed manually to AWS EC2 for now, with future plans to automate deployment via CI/CD using GitHub Actions and AWS CodeDeploy.

### 🔄 Planned CI/CD Workflow

- GitHub Actions for build and test  
- Artifact creation (`.jar`)  
- AWS CodeDeploy for EC2 deployment  
- `start.sh` for launching the Spring Boot app  

---

## 🔐 HTTPS Configuration

EduFleet uses Spring Boot's embedded Tomcat server.

ongoing...
