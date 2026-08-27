package com.studentmanagement.util;

import com.studentmanagement.exception.InvalidStudentDataException;

/**
 * Centralized validation rules for student data.
 * Keeping validation here (instead of scattered in Main) avoids duplicate code.
 */
public class InputValidator {

    public static void validateName(String name) throws InvalidStudentDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidStudentDataException("Name cannot be empty.");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new InvalidStudentDataException("Name must contain only letters and spaces.");
        }
    }

    public static void validateAge(int age) throws InvalidStudentDataException {
        if (age <= 0 || age > 100) {
            throw new InvalidStudentDataException("Age must be between 1 and 100.");
        }
    }

    public static void validateCourse(String course) throws InvalidStudentDataException {
        if (course == null || course.trim().isEmpty()) {
            throw new InvalidStudentDataException("Course cannot be empty.");
        }
    }

    public static void validateMarks(double marks) throws InvalidStudentDataException {
        if (marks < 0 || marks > 100) {
            throw new InvalidStudentDataException("Marks must be between 0 and 100.");
        }
    }
}
