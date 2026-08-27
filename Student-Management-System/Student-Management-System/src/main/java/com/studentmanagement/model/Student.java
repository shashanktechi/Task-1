package com.studentmanagement.model;

/**
 * Represents a single student record.
 * Demonstrates encapsulation (private fields + public getters/setters)
 * and method overloading (two constructors).
 */
public class Student {

    private int id;
    private String name;
    private int age;
    private String course;
    private double marks;

    // Overloaded constructor 1 - used when creating a brand new student (id auto-assigned later)
    public Student(String name, int age, String course, double marks) {
        this.name = name;
        this.age = age;
        this.course = course;
        this.marks = marks;
    }

    // Overloaded constructor 2 - used when loading an existing student back from file (id already known)
    public Student(int id, String name, int age, String course, double marks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    /**
     * Converts this student into a single pipe-delimited line for file storage.
     * Format: id|name|age|course|marks
     */
    public String toFileFormat() {
        return id + "|" + name + "|" + age + "|" + course + "|" + marks;
    }

    /**
     * Rebuilds a Student object from a stored file line.
     */
    public static Student fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        int age = Integer.parseInt(parts[2].trim());
        String course = parts[3].trim();
        double marks = Double.parseDouble(parts[4].trim());
        return new Student(id, name, age, course, marks);
    }

    @Override
    public String toString() {
        return String.format("%-5d %-20s %-5d %-15s %-6.2f", id, name, age, course, marks);
    }
}
