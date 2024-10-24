# Backend Project

## Project Overview
This is a backend project built using **Java 18** and **Spring Boot** framework. The project implements an application with multiple entities, user roles, and functionalities, focusing on managing articles, courses, events, and user interactions.

## Features
- **User Management**: Allows registration and login of users with various roles such as *Administrator*, *Tutor*, *Professor*, *Student*, *Pastor*, and *Moderator*.
- **Articles**:
  - Published by authorized users.
  - Can contain text, images, and videos (stored on Amazon cloud services).
  - Public articles viewable by all users.
  - Course-specific articles visible only to registered students and professors of that course.
  - **Moderators** are responsible for accepting articles before they are published.
- **Roles and Permissions**:
  - **Pastors**: Can delete users and assign roles.
  - **Administrators**: Can create, delete, and manage events.
  - **Tutors**: Can manage courses and the enrollment of students.
  - **Moderators**: Responsible for reviewing and accepting articles, deleting inappropriate comments, and enforcing community guidelines.
  - **Users**: Only registered users can comment on articles.
- **Courses**:
  - Each course is managed by a *Tutor*.
  - Professors and students can be assigned to courses.
  - Enrollment can be removed only by the tutor.
- **Events**:
  - Created and managed exclusively by administrators.
- **Password Recovery**: Through email-based password reset.
- **Authentication and Authorization**:
  - Implemented using **Spring Security** and **JWT (JSON Web Tokens)**.

## Technologies Used
- **Java 18**
- **Spring Boot**
- **Spring Security** (for authentication and authorization)
- **JWT (JSON Web Tokens)**
- **Hibernate** (for ORM)
- **Embedded Tomcat Server**
- **SQL Database** (for persistent data storage)
- **Amazon Cloud** (for media storage: images and videos)

## Getting Started

### Prerequisites
- **Java 11** or higher installed.
- **Maven** installed for dependency management.
- **SQL Database** setup with the required tables and relationships.
- **Amazon S3 Bucket** for storing media files.

### Installation
1. **Clone the repository**:
   ```bash
   git clone https://github.com/ToniViz/churchApp
   cd churchApp
Install dependencies using Maven:

bash
Copy code
mvn clean install
Configure the database:

Update the application.properties file with your SQL database connection details.
Amazon S3 Setup:

Add your AWS S3 credentials and bucket configuration in the application.properties file.
Run the project:

bash
Copy code
mvn spring-boot:run
Usage
-Visit the home page to view common articles, which are visible to all visitors.
-Register and login to gain additional access depending on your assigned role.
-Manage articles (publish, edit, delete) based on the roles and permissions assigned.
-Enroll in courses and access course-specific content.
-Create and manage events as an administrator.
-Review articles and moderate comments as a moderator.
-Recover your password via email if needed.
