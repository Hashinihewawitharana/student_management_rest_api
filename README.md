Student Management API

A Spring Boot REST API for managing student records with full CRUD operations, validation, pagination, sorting, search, and global exception handling.


Tech Stack

Java 17+
Spring Boot
Spring Data JPA
Hibernate
PostgreSQL
Lombok
Jakarta Validation
Maven


Project Setup Steps

   Prerequisites

Make sure you have:

 Java 17+
Maven or Maven Wrapper (`mvnw`)
PostgreSQL  running
 IDE (IntelliJ )


Step 1 — Clone the Project

git clone https://github.com/your-repo/student-management.git
cd student-management


Step 2 — Configure Database

Update `application.properties`:

spring.datasource.url=jdbc:postgresql://localhost:5432/studentdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

Step 3 — Install Dependencies

If you have Maven installed:
mvn clean install

Otherwise, use Maven Wrapper (no separate Maven installation needed):
./mvnw.cmd clean install


Step 4 — Run the Application

If Maven is installed:
mvn spring-boot:run

If Maven is not installed or you prefer Maven Wrapper:

./mvnw.cmd spring-boot:run

Application runs at:

http://localhost:8080

 API Endpoints Documentation

Base URL: http://localhost:8080

1. Create Student

POST       http://localhost:8080/api/students

Request Body:

json
{
  "id": "TEC001",
  "name": "John Doe",
  "email": "john@gmail.com",
  "course": "ICT",
  "age": 22
}

Responses:

201 Created→ Student created
409 Conflict→ Duplicate ID or Email
400 Bad Request → Student is not found


2. Get Student by ID

GET  http://localhost:8080/api/students/{id}

http://localhost:8080/api/students/TEC001

Responses:
200 OK-Student found
404 Not Found-student ID does not exist in the system.

3. Get All Students (Pagination + Sorting + Search)

GET   http://localhost:8080/api/students

Parameter	Default	Description
search	none	Search by name/course
page	0	Page number
size	10	Page size
sortBy	id	Field to sort (id, name, email, course, age)
sortDir	asc	asc / desc

 Example:
http://localhost:8080 /api/students?search=john&page=0&size=5&sortBy=name&sortDir=desc

Response:
200 OK-Student found

4. Update Student

PUT http://localhost:8080/api/students /{id}

Request Body:
{
  "name": "Updated Name",
  "email": "updated@gmail.com",
  "course": "BST",
  "age": 25
}

Responses:

200 OK-Student is updated
404 Not Found-student ID does not exist in the system.
409 Conflict(duplicate email)- same email,ID already exists


5. Delete Student

DELETE http://localhost:8080 /api/students/{id}

Responses:

204 No Content-student deleted successfully.
404 Not Found- student ID does not exist in the system.

 Validation Rules
Field	Rule
id	Required, unique
name	Not blank
email	Valid email, unique
course	Must be ICT, EGT, BST
age	Min 18

Exception Handling

Your  GlobalExceptionHandler returns:

409 Conflict
 Duplicate email
 Duplicate ID

404 Not Found
          Student not found

400 Validation Errors

Returned as:

{
  "email": "Email should be valid",
  "name": "Name is required"
}

Testing Using Swagger

If Swagger is enabled:
http://localhost:8080/swagger-ui/index.html


