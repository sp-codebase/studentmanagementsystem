package sms.service;

import sms.model.Student;
import sms.repo.StudentRepository;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Business logic, validation, and higher-level operations.
 */
public class StudentService {
    private final StudentRepository repo;
    private final Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public Student addStudent(String name, Integer age, String email, String course) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name required");
        if (age != null && (age < 0 || age > 150)) throw new IllegalArgumentException("Invalid age");
        if (email != null && !email.trim().isEmpty() && !emailPattern.matcher(email).matches())
            throw new IllegalArgumentException("Invalid email");

        Student s = new Student(name.trim(), age, email == null ? null : email.trim(),
                course == null ? null : course.trim());
        return repo.add(s);
    }

    public Student getStudent(int id) {
        return repo.findById(id);
    }

    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    public boolean updateStudent(int id, String name, Integer age, String email, String course) {
        Student existing = repo.findById(id);
        if (existing == null) return false;

        if (name != null && !name.trim().isEmpty()) existing.setName(name.trim());
        if (age != null) {
            if (age < 0 || age > 150) throw new IllegalArgumentException("Invalid age");
            existing.setAge(age);
        }
        if (email != null) {
            if (!email.trim().isEmpty() && !emailPattern.matcher(email).matches())
                throw new IllegalArgumentException("Invalid email");
            existing.setEmail(email.trim().isEmpty() ? null : email.trim());
        }
        if (course != null) existing.setCourse(course.trim());
        return repo.update(existing);
    }

    public boolean deleteStudent(int id) {
        return repo.delete(id);
    }
}
