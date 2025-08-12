# 🚀 AI-Powered Smart Recruitment System (Backend)
An intelligent recruitment backend built with **Java** and **Spring Boot** to streamline hiring through automation, AI insights, and efficient workflows.

## ✨ Features

* **🔐 Secure Authentication** – Role-based access control with **JWT**
* **📄 Job Posting & Applications** – Full CRUD + Applicant Tracking
* **🤖 Resume Scoring System** – AI-assisted skill & keyword matching
* **📊 Skill Gap Analysis** – Identify strengths & improvement areas
* **📅 Interview Scheduler** – Integrated with **Google Calendar OAuth**
* **🔔 Multi-channel Notifications** – Email, SMS (**Twilio**), Slack & in-app WebSockets
* **📈 Analytics Dashboard** – Recruitment insights & performance tracking



## 🛠 Tech Stack

* **Backend:** Java, Spring Boot, Spring Security, WebSocket
* **Database:** MySQL
* **Auth:** JWT
* **Integrations:** Google Calendar API, Twilio API, Slack Webhooks



## 📂 Project Structure

src/
 ├── controller/        # REST API endpoints
 ├── service/           # Business logic & integrations
 ├── repository/        # JPA Repositories
 ├── entity/            # Database models
 └── security/          # JWT & role-based security

## 🚀 Getting Started

### 1️⃣ Clone the Repository

bash
git clone https://github.com/yourusername/ai-smart-recruitment.git
cd ai-smart-recruitment


### 2️⃣ Configure Environment Variables

Create a `.env` file or use `application.properties`:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/recruitment
spring.datasource.username=root
spring.datasource.password=yourpassword
jwt.secret=your_jwt_secret
google.calendar.credentials=path/to/credentials.json
twilio.sid=your_twilio_sid
twilio.token=your_twilio_token
slack.webhook.url=your_slack_webhook_url


### 3️⃣ Build & Run

bash
mvn clean install
mvn spring-boot:run


## 📌 API Documentation

API endpoints are documented via **Swagger**.
Once running, visit:


## 📧 Contact

**Rehan Shaikh** – [rehanshaikh.dev@gmail.com](mailto:rehanshaikh.dev@gmail.com)


## 🏷 Tags

`Java` `SpringBoot` `JWT` `AIRecruitment` `MySQL` `WebSocket` `Twilio` `GoogleCalendarAPI`
