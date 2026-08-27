package com.studentmanagement.exception;

/**
 * Thrown when an operation (update/delete/search) targets a student ID
 * that does not exist in the system.
 */
public class StudentNotFoundException extends Exception {

    public StudentNotFoundException(int id) {
        super("No student found with ID: " + id);
    }
}
