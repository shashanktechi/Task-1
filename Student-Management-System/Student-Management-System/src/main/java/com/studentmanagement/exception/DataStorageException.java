package com.studentmanagement.exception;

/**
 * Thrown when an operation fails to read or write data to the persistent storage.
 */
public class DataStorageException extends Exception {

    public DataStorageException(String message) {
        super(message);
    }

    public DataStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
