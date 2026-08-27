package com.studentmanagement.service;

import com.studentmanagement.exception.DataStorageException;
import com.studentmanagement.exception.InvalidStudentDataException;
import com.studentmanagement.exception.StudentNotFoundException;
import com.studentmanagement.model.Student;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.util.InputValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Business logic layer. Main.java never touches the repository or the
 * raw ArrayList directly - it only calls methods on this class.
 */
public class StudentService {

    private final StudentRepository repository;
    private List<Student> students;
    private int nextId;

    public StudentService(StudentRepository repository) throws DataStorageException {
        this.repository = repository;
        this.students = new ArrayList<>(repository.loadAll());
        this.nextId = computeNextId();
    }

    private int computeNextId() {
        int max = 0;
        for (Student s : students) {
            if (s.getId() > max) {
                max = s.getId();
            }
        }
        return max + 1;
    }

    public Student addStudent(String name, int age, String course, double marks)
            throws InvalidStudentDataException, DataStorageException {
        InputValidator.validateName(name);
        InputValidator.validateAge(age);
        InputValidator.validateCourse(course);
        InputValidator.validateMarks(marks);

        Student student = new Student(nextId, name, age, course, marks);
        nextId++;
        students.add(student);
        try {
            repository.saveAll(students);
        } catch (DataStorageException e) {
            students.remove(student);
            nextId--;
            throw e;
        }
        return student;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public List<Student> getStudentsSortedByMarks() {
        List<Student> sortedList = new ArrayList<>(students);
        sortedList.sort(Comparator.comparingDouble(Student::getMarks).reversed());
        return sortedList;
    }

    public Student searchById(int id) throws StudentNotFoundException {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        throw new StudentNotFoundException(id);
    }

    public List<Student> searchByName(String name) {
        List<Student> results = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name.trim())) {
                results.add(s);
            }
        }
        return results;
    }

    public void updateStudent(int id, String name, int age, String course, double marks)
            throws StudentNotFoundException, InvalidStudentDataException, DataStorageException {
        Student student = searchById(id); // throws if not found

        InputValidator.validateName(name);
        InputValidator.validateAge(age);
        InputValidator.validateCourse(course);
        InputValidator.validateMarks(marks);

        String oldName = student.getName();
        int oldAge = student.getAge();
        String oldCourse = student.getCourse();
        double oldMarks = student.getMarks();

        student.setName(name);
        student.setAge(age);
        student.setCourse(course);
        student.setMarks(marks);

        try {
            repository.saveAll(students);
        } catch (DataStorageException e) {
            student.setName(oldName);
            student.setAge(oldAge);
            student.setCourse(oldCourse);
            student.setMarks(oldMarks);
            throw e;
        }
    }

    public void deleteStudent(int id) throws StudentNotFoundException, DataStorageException {
        Student student = searchById(id); // throws if not found
        int index = students.indexOf(student);
        students.remove(student);
        try {
            repository.saveAll(students);
        } catch (DataStorageException e) {
            students.add(index, student);
            throw e;
        }
    }
}
