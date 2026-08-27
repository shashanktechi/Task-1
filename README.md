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

```text
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

```text
1. Add Student
2. View All Students
3. Update Student
4. Delete Student
5. Search Student
6. Sort Students by Marks
7. Exit
```

## Screenshots

Here are some screenshots demonstrating the application's functionality:

- **Add Student:**  
  ![Add Student](OUTPUT%20Screen%20Shots/ADD1.png)
- **View All Students:**  
  ![View Students](OUTPUT%20Screen%20Shots/View1.png)
- **Update Student:**  
  ![Update Student](OUTPUT%20Screen%20Shots/Update1.png)
- **Delete Student:**  
  ![Delete Student](OUTPUT%20Screen%20Shots/Del.png)

## How I Validated It

The application has been manually tested across all core functional paths to ensure robust behavior and data integrity:

1. **Add:** Attempted to add valid students (success) and invalid students (e.g., negative age, blank names) to ensure `InputValidator` and `InvalidStudentDataException` handle bad data gracefully.
2. **View:** Verified the table format perfectly aligns and displays all currently stored student records.
3. **Update:** Modified an existing student's details and ensured both the in-memory list and storage file reflect the change.
4. **Search:** Confirmed that searching by both ID and Name returns the expected results, handling edge cases like non-existent records correctly via `StudentNotFoundException`.
5. **Delete:** Removed a student by ID, verifying that the student no longer appears in the "View All Students" list and is permanently erased.
6. **File Persistence Confirmation:** Checked `data/students.txt` after every mutating operation (add, update, delete) to confirm the data is instantly saved and remains consistent after restarting the application. Evaluated the new rollback handling by confirming `DataStorageException` prevents memory corruption if disk writes fail.

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
- Package as an executable JAR

## Author

Kisannagari Shashank — B.Tech CSE (Final Year), Madanapalle Institute of
Technology & Science
