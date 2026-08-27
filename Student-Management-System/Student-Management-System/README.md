# Console-Based Student Management System

A modular, console-based Student Management System built in **Core Java** as
Task 1 of the Java Full Stack Development Internship (Sumerix Global).

## Features

- Add Student
- View All Students
- Update Student Record
- Delete Student
- Search Student (by ID or Name)
- Persistent storage using file handling (`data/students.txt`)
- Input validation with custom exceptions
- Clean OOP structure with separated layers

## Tech Stack

- Java 21
- Core Java (no frameworks) — Collections, File I/O, Exception Handling
- Maven (build tool)

## Project Structure

```
Student-Management-System/
├── src/main/java/com/studentmanagement/
│   ├── model/         -> Student.java
│   ├── repository/    -> StudentRepository.java (file persistence)
│   ├── service/        -> StudentService.java (business logic)
│   ├── util/           -> InputValidator.java
│   ├── exception/      -> StudentNotFoundException, InvalidStudentDataException
│   └── main/           -> Main.java (console UI / entry point)
├── data/
│   └── students.txt     -> stores student records
├── pom.xml
├── .gitignore
└── README.md
```

## How to Run

### Option 1: IntelliJ IDEA / Eclipse / VS Code
1. Open the project folder as a Maven project.
2. Run `Main.java` (located in `com.studentmanagement.main`).

### Option 2: Command line (Maven installed)
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.studentmanagement.main.Main"
```

### Option 3: Plain javac/java
```bash
cd src/main/java
javac com/studentmanagement/**/*.java com/studentmanagement/main/Main.java
java com.studentmanagement.main.Main
```

## Sample Menu

```
1. Add Student
2. View All Students
3. Update Student
4. Delete Student
5. Search Student
6. Exit
```

## Design Notes

- **Layered architecture**: `main` (UI) → `service` (business rules) →
  `repository` (file I/O) → `model` (data). Main never touches the file
  directly, and the repository never contains business rules.
- **Validation** is centralized in `InputValidator` so rules aren't
  duplicated across add/update flows.
- **Custom exceptions** (`StudentNotFoundException`,
  `InvalidStudentDataException`) make error handling explicit instead of
  relying on generic exceptions.
- Data is stored as pipe-delimited (`|`) lines in `data/students.txt` and
  the full file is rewritten after every add/update/delete, so the file
  always reflects the current in-memory state.

## Future Enhancements

- Migrate storage from flat file to MySQL via JDBC (Task 2)
- Add unit tests with JUnit
- Add sorting/filtering (by marks, course)
- Package as an executable JAR

## Author

Kisannagari Shashank — B.Tech CSE (Final Year), Madanapalle Institute of
Technology & Science
