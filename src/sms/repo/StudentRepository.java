package sms.repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import sms.db.DBUtil;
import sms.model.Student;

public class StudentRepository {

    public Student addStudent(String name, Integer age, String email, String course) throws SQLException {
        String sql = "INSERT INTO students(name, age, email, course) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);

            if (age == null) ps.setNull(2, Types.INTEGER);
            else ps.setInt(2, age);

            if (email == null) ps.setNull(3, Types.VARCHAR);
            else ps.setString(3, email);

            ps.setString(4, course);

            ps.executeUpdate();

            int id = 0;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) id = rs.getInt(1);
            }
            return new Student(id, name, age, email, course);
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        String sql = "SELECT id, name, age, email, course FROM students ORDER BY id";
        List<Student> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Student getStudent(int id) throws SQLException {
        String sql = "SELECT id, name, age, email, course FROM students WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public boolean updateStudent(int id, String name, Integer age, String email, String course) throws SQLException {
        Student existing = getStudent(id);
        if (existing == null) return false;

        String newName   = name   != null ? name   : existing.getName();
        Integer newAge   = age    != null ? age    : existing.getAge();
        String newEmail  = email  != null ? email  : existing.getEmail();
        String newCourse = course != null ? course : existing.getCourse();

        String sql = "UPDATE students SET name = ?, age = ?, email = ?, course = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newName);

            if (newAge == null) ps.setNull(2, Types.INTEGER);
            else ps.setInt(2, newAge);

            if (newEmail == null) ps.setNull(3, Types.VARCHAR);
            else ps.setString(3, newEmail);

            ps.setString(4, newCourse);
            ps.setInt(5, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int ageVal = rs.getInt("age");
        Integer age = rs.wasNull() ? null : ageVal;
        String email = rs.getString("email");
        String course = rs.getString("course");
        return new Student(id, name, age, email, course);
    }
}