package com.studentmanagement.main;

import com.studentmanagement.exception.DataStorageException;
import com.studentmanagement.exception.InvalidStudentDataException;
import com.studentmanagement.exception.StudentNotFoundException;
import com.studentmanagement.model.Student;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.service.StudentService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point. Handles only the console menu and user interaction -
 * all real logic lives in StudentService.
 */
public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static StudentRepository REPOSITORY;
    private static StudentService SERVICE;

    public static void main(String[] args) {
        try {
            REPOSITORY = new StudentRepository("data/students.txt");
            SERVICE = new StudentService(REPOSITORY);
        } catch (DataStorageException e) {
            System.out.println("Fatal error: " + e.getMessage());
            return;
        }

        boolean running = true;

        System.out.println("=========================================");
        System.out.println("   STUDENT MANAGEMENT SYSTEM");
        System.out.println("=========================================");

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> handleAddStudent();
                case 2 -> handleViewAll();
                case 3 -> handleUpdateStudent();
                case 4 -> handleDeleteStudent();
                case 5 -> handleSearch();
                case 6 -> handleSortByMarks();
                case 7 -> {
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select 1-7.");
            }
            System.out.println();
        }
        SCANNER.close();
    }

    private static void printMenu() {
        System.out.println("-----------------------------------------");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Search Student");
        System.out.println("6. Sort Students by Marks");
        System.out.println("7. Exit");
        System.out.println("-----------------------------------------");
    }

    private static void handleAddStudent() {
        try {
            Student input = readStudentDetails("");

            Student student = SERVICE.addStudent(input.getName(), input.getAge(), input.getCourse(), input.getMarks());
            System.out.println("Student added successfully with ID: " + student.getId());
        } catch (InvalidStudentDataException e) {
            System.out.println("Could not add student: " + e.getMessage());
        } catch (DataStorageException e) {
            System.out.println("Failed to save student data: " + e.getMessage());
        }
    }

    private static void handleViewAll() {
        List<Student> students = SERVICE.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.printf("%-5s %-20s %-5s %-15s %-6s%n", "ID", "Name", "Age", "Course", "Marks");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void handleSortByMarks() {
        List<Student> students = SERVICE.getStudentsSortedByMarks();
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.printf("%-5s %-20s %-5s %-15s %-6s%n", "ID", "Name", "Age", "Course", "Marks");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void handleUpdateStudent() {
        try {
            int id = readInt("Enter student ID to update: ");
            Student input = readStudentDetails("new ");

            SERVICE.updateStudent(id, input.getName(), input.getAge(), input.getCourse(), input.getMarks());
            System.out.println("Student updated successfully.");
        } catch (StudentNotFoundException | InvalidStudentDataException e) {
            System.out.println("Could not update student: " + e.getMessage());
        } catch (DataStorageException e) {
            System.out.println("Failed to save student data: " + e.getMessage());
        }
    }

    private static void handleDeleteStudent() {
        try {
            int id = readInt("Enter student ID to delete: ");
            SERVICE.deleteStudent(id);
            System.out.println("Student deleted successfully.");
        } catch (StudentNotFoundException e) {
            System.out.println("Could not delete student: " + e.getMessage());
        } catch (DataStorageException e) {
            System.out.println("Failed to save student data: " + e.getMessage());
        }
    }

    private static void handleSearch() {
        System.out.println("Search by: 1. ID   2. Name");
        int mode = readInt("Enter choice: ");

        if (mode == 1) {
            int id = readInt("Enter student ID: ");
            try {
                Student s = SERVICE.searchById(id);
                System.out.println("Found: " + s);
            } catch (StudentNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else if (mode == 2) {
            System.out.print("Enter student name: ");
            String name = SCANNER.nextLine();
            List<Student> results = SERVICE.searchByName(name);
            if (results.isEmpty()) {
                System.out.println("No student found with name: " + name);
            } else {
                results.forEach(System.out::println);
            }
        } else {
            System.out.println("Invalid search option.");
        }
    }

    // ---- Safe input helpers: keep InputMismatchException out of business logic ----

    private static Student readStudentDetails(String prefix) {
        System.out.print("Enter " + prefix + "name: ");
        String name = SCANNER.nextLine();
        int age = readInt("Enter " + prefix + "age: ");
        System.out.print("Enter " + prefix + "course: ");
        String course = SCANNER.nextLine();
        double marks = readDouble("Enter " + prefix + "marks (0-100): ");
        return new Student(name, age, course, marks);
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(SCANNER.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
