# 🎓 E-Learning API - From Strategy to Practice: A Blueprint for Designing a Scalable and Robust System

![GitHub](https://img.shields.io/github/license/yourusername/e-learning-api)
![Microservices](https://img.shields.io/badge/Microservices-architecture-blue)
![Quality Gate Status](https://img.shields.io/badge/Quality%20Gate-Passed-green)
![Bugs](https://img.shields.io/badge/Bugs-0-green)
![Security Hotspots](https://img.shields.io/badge/Security%20Hotspots-0-green)
![Code Smells](https://img.shields.io/badge/Code%20Smells-0-green)
![Duplicated Lines (%)](https://img.shields.io/badge/Duplicated%20Lines-2%25-yellow)
![Coverage](https://img.shields.io/badge/Coverage-60%25-yellow)
![Coverage - Class Level](https://img.shields.io/badge/Coverage%20(Class%20Level)-100%25-green)
![Coverage - Method Level](https://img.shields.io/badge/Coverage%20(Method%20Level)-74%25-green)

This project is an E-Learning API built as a microservices architecture. The project considers aspects like maintainability, evolvability, and scalability, providing a blueprint to a real-world application. 

The API allows users to interact with various media types and receive recommendations based on a scoring system. All the operations related to media, ratings, and recommendations are handled by individual microservices. Detailed research underpinning this project can be found at this link [reserchgate/emanuel](https://www.researchgate.net/publication/xxxxxxx).

## 📘 Contents

1. [Project Structure](#project-structure)
2. [Strategies](#strategies)
3. [Architecture](#architecture)

## 📚 Project Structure

The project is structured as a microservice API. There are several folders containing various components of the project:

1. **0-DBScripts:** Scripts for creating your own database.
2. **1-Tools:** Batch files for starting the servers and analyzing all the microservices.
3. **2-Configs:** Configuration files to set up your own Logstash & Prometheus servers and a dashboard for Grafana ([Dashboard](https://grafana.com/grafana/dashboards/12900-springboot-apm-dashboard/)).
4. **Test Data & Demo Scripts:** The last two folders contain some test data and batch files to demo the app.

## 💼 Strategies

Several strategies were implemented in this project, each with a specific purpose:

1. **Architectural Styles:** I used REST because of its simplicity, wide acceptance, compatibility with HTTP, and compatibility with many frameworks.
2. **Architectural Patterns:** The project uses a microservices architecture for scalability, flexibility, and modularity.
3. **Database Design:** The database design follows classic principles like ACID, CAP theorem, and normalization.
4. **Web Security:** Input validation and sanitization, cookies restrains, JPA to prevent SQL injection, and session timeouts for session hijacking.
5. **Stateless:** Each request contains all the information necessary for the server.
6. **Loose Coupling:** Microservices can operate independently without significantly impacting others.
7. **Evolvability:** Easy to modify or add new features without affecting other parts of the system.
8. **Resilience & Mitigation:** Circuit Breaker, Retry, Rate Limiter, and Time Limiter are used for resilience. Shifting load, caching, and scheduling are used for mitigation.

More details on each strategy can be found in the [research paper](https://www.researchgate.net/publication/xxxxxxx).

## 🏛️ Architecture

The system is comprised of several microservices, each with a specific role:

1. **Media Service:** Handles all operations related to media. Utilizes five types of DTOs: media, audio document, image, and video.
2. **Rating Service:** Handles all rating operations.
3. **Recommendation Service:** Provides different media recommendations based on a scoring mechanism.
4. **Notification Microservice:** Sends notifications to the system administrator in different situations.
5. **Discovery Server:** Naming registry
6. **Load Balancer:** Manages workload
7. **Observability:** Slf4j, ELK stack, Actuator, Prometheus, Grafana
8. **Zipkin Server:** Collects and looks up data from interconnected systems
9. **Redis Server:** Caching
10. **Starter Library:** Extracts all the common objects and functionality
11. **Jenkins Server:** Clean, build, and test if there are any commits
12. **SonarQube Server** Analizes the quality of the project
13. **Swagger Server** Testing and Documentation

## 🚀 Getting Started

### Prerequisites

- Postgres & all the servers mentioned in the arhitecture
- Java 8 or higher
- Any Java-supported IDE or text editor

### Usage

Each microservice is standalone, and you can run them independently.

## 📜 License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## 📮 Contact

- GitHub [@EmanuelButoiGit](https://github.com/{EmanuelButoiGit})
- LinkedIn [Emanuel-Sebastian Butoi](https://www.linkedin.com/in/{emanuel-sebastian-butoi-929271213})

---

<p align="center">
  Keep coding and improving! 🚀
</p>
