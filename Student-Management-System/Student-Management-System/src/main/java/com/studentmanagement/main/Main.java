package com.studentmanagement.main;

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

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentRepository repository = new StudentRepository("data/students.txt");
    private static final StudentService service = new StudentService(repository);

    public static void main(String[] args) {
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
                case 6 -> {
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select 1-6.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("-----------------------------------------");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Search Student");
        System.out.println("6. Exit");
        System.out.println("-----------------------------------------");
    }

    private static void handleAddStudent() {
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            int age = readInt("Enter age: ");
            System.out.print("Enter course: ");
            String course = scanner.nextLine();
            double marks = readDouble("Enter marks (0-100): ");

            Student student = service.addStudent(name, age, course, marks);
            System.out.println("Student added successfully with ID: " + student.getId());
        } catch (InvalidStudentDataException e) {
            System.out.println("Could not add student: " + e.getMessage());
        }
    }

    private static void handleViewAll() {
        List<Student> students = service.getAllStudents();
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
            System.out.print("Enter new name: ");
            String name = scanner.nextLine();
            int age = readInt("Enter new age: ");
            System.out.print("Enter new course: ");
            String course = scanner.nextLine();
            double marks = readDouble("Enter new marks (0-100): ");

            service.updateStudent(id, name, age, course, marks);
            System.out.println("Student updated successfully.");
        } catch (StudentNotFoundException | InvalidStudentDataException e) {
            System.out.println("Could not update student: " + e.getMessage());
        }
    }

    private static void handleDeleteStudent() {
        try {
            int id = readInt("Enter student ID to delete: ");
            service.deleteStudent(id);
            System.out.println("Student deleted successfully.");
        } catch (StudentNotFoundException e) {
            System.out.println("Could not delete student: " + e.getMessage());
        }
    }

    private static void handleSearch() {
        System.out.println("Search by: 1. ID   2. Name");
        int mode = readInt("Enter choice: ");

        if (mode == 1) {
            int id = readInt("Enter student ID: ");
            try {
                Student s = service.searchById(id);
                System.out.println("Found: " + s);
            } catch (StudentNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else if (mode == 2) {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            List<Student> results = service.searchByName(name);
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

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
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
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
