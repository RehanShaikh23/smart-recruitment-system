🚀 AI-Powered Smart Recruitment System (Backend)
An intelligent recruitment backend designed to automate hiring, assist recruiters with AI insights, and streamline the entire process — from job posting to candidate selection.

📌 Features
🔐 Authentication & Authorization
Role-based access control (Recruiter, Candidate, Admin)

Secure JWT-based authentication

Password hashing using BCrypt

📄 Job Posting & Application Management
Create, read, update, delete (CRUD) jobs

Track applications with statuses (Applied, Shortlisted, Rejected, etc.)

Candidate profile management

🤖 AI-Driven Insights
Resume Scoring System – Match skills & keywords for better candidate shortlisting

Skill Gap Analysis – Identify missing skills for career improvement

📅 Interview Scheduling
Integrated with Google Calendar API (OAuth 2.0)

Seamless booking for recruiters and candidates

📢 Multi-channel Notifications
Email (JavaMailSender)

SMS (Twilio API)

Slack Webhooks

Real-time in-app updates via WebSockets

📊 Analytics Dashboard
Job statistics (applications, shortlisted, rejected)

Recruitment performance tracking

🛠 Tech Stack
Backend: Java, Spring Boot, Spring Security
Database: MySQL, JPA/Hibernate
Authentication: JWT, BCrypt
Real-time: WebSocket
APIs & Integrations: Google Calendar API, Twilio API, Slack Webhooks
Tools: Lombok, Maven

📂 Project Structure
bash
Copy
Edit
src/
 ├── main/
 │   ├── java/com/AI_Powered/Smart/Recruitment/SmartRecruitment
 │   │   ├── config        # Security, WebSocket, and API configs
 │   │   ├── controller   # REST API endpoints
 │   │   ├── dto          # Data Transfer Objects
 │   │   ├── entity       # JPA entities
 │   │   ├── exception    # Custom exceptions
 │   │   ├── repository   # Spring Data JPA repositories
 │   │   ├── service      # Business logic layer
 │   │   └── util         # Utility classes
 │   └── resources/
 │       ├── application.properties
 │       └── static/templates (if any)
🚀 Getting Started
1️⃣ Clone the Repository
bash
Copy
Edit
git clone https://github.com/yourusername/ai-powered-smart-recruitment.git
cd ai-powered-smart-recruitment
2️⃣ Configure the Database
Edit application.properties:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/smart_recruitment
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
3️⃣ Set Up Environment Variables
For API keys:

properties
Copy
Edit
# JWT
jwt.secret=your_secret_key

# Google Calendar
google.client.id=your_client_id
google.client.secret=your_client_secret

# Twilio
twilio.accountSid=your_sid
twilio.authToken=your_token
twilio.phoneNumber=your_number

# Slack
slack.webhook.url=your_webhook_url
4️⃣ Run the Application
bash
Copy
Edit
mvn spring-boot:run
📬 API Endpoints Overview
Method	Endpoint	Description	Role
POST	/api/auth/register	Register new user	Public
POST	/api/auth/login	Login and get JWT token	Public
POST	/api/jobs	Create a job posting	Recruiter
GET	/api/jobs	List all jobs	Public
POST	/api/applications/{jobId}	Apply to a job	Candidate
PATCH	/api/applications/{id}/status	Update application status	Recruiter
GET	/api/analytics/job/{jobId}	Job application stats	Recruiter

(Full API documentation in /docs or Postman collection)

🧑‍💻 Author
Rehan Shaikh
📧 Email: rehanshaikh.dev@gmail.com
🌐 LinkedIn | GitHub

⭐ Contribute
If you find this project useful, consider giving it a star ⭐ and contributing via pull requests.

