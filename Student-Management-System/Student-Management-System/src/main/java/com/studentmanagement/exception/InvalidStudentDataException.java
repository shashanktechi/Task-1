package com.studentmanagement.exception;

/**
 * Thrown when user-supplied student data fails validation
 * (e.g. empty name, negative age, marks out of range).
 */
public class InvalidStudentDataException extends Exception {

    public InvalidStudentDataException(String message) {
        super(message);
    }
}
