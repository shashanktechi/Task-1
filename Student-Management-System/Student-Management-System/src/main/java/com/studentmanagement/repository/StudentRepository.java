package com.studentmanagement.repository;

import com.studentmanagement.exception.DataStorageException;
import com.studentmanagement.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all direct file I/O for student records.
 * This is the ONLY class that talks to the filesystem — the rest of the
 * application works purely with in-memory Student objects.
 */
public class StudentRepository {

    private final String filePath;

    public StudentRepository(String filePath) throws DataStorageException {
        this.filePath = filePath;
        ensureFileExists();
    }

    private void ensureFileExists() throws DataStorageException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        try {
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new DataStorageException("Could not initialize data file.", e);
        }
    }

    /**
     * Loads every student currently stored in the file.
     */
    public List<Student> loadAll() throws DataStorageException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    students.add(Student.fromFileFormat(line));
                }
            }
        } catch (IOException e) {
            throw new DataStorageException("Error reading student data.", e);
        }
        return students;
    }

    /**
     * Overwrites the file with the full current list of students.
     * Called after every add/update/delete so the file always reflects
     * the latest in-memory state.
     */
    public void saveAll(List<Student> students) throws DataStorageException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Student s : students) {
                writer.write(s.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DataStorageException("Error saving student data.", e);
        }
    }
}
