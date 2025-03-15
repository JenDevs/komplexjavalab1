Lab1Books is a RESTful API for managing books, built using Jakarta EE 11, WildFly Preview, and PostgreSQL.
It provides endpoints for retrieving, filtering, creating, updating, and deleting books.
The API includes validation, error handling, and pagination for efficient data management.
It does not use Spring Boot, and database operations are handled via Jakarta Data repositories.

Features
Retrieve all books or fetch a specific book by ID.
Filter books by author, title, and genre.
Combine filters for author & title or author & genre.
Create new books with validation to prevent duplicates.
Update existing books with partial updates supported.
Delete books safely with proper error handling.
To build and run the application
Build the Docker image for the application:
docker build -t lab1books .


To build and run the applicaiton

Run all services defined in docker-compose.yaml:
docker-compose up --build

Check that both containers are running:
docker ps

If any container is missing, run one of the following commands, depending on which one is missing:
docker-compose up book-api
docker-compose up my_postgresnew

Then, you can check that the database is available here:
http://localhost:8080/Lab1Books-1.0-SNAPSHOT/api/books

Use Insomnia to test posting and modifying data in the database.
