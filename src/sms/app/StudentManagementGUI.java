package sms.app;

import sms.model.Student;
import sms.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentManagementGUI extends JFrame {

    private final StudentService studentService;

    // UI components
    private JTextField idField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField emailField;
    private JTextField courseField;
    private JTextArea outputArea;

    public StudentManagementGUI(StudentService studentService) {
        this.studentService = studentService;

        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null); // center window

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Main layout
        setLayout(new BorderLayout(10, 10));

        // Top panel - form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        idField = new JTextField();      // will be useful later for view/update/delete
        nameField = new JTextField();
        ageField = new JTextField();
        emailField = new JTextField();
        courseField = new JTextField();

        formPanel.add(new JLabel("ID (for search/update/delete):"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Age (blank = none):"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Email (blank = none):"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Course:"));
        formPanel.add(courseField);

        // Middle panel - buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JButton addButton = new JButton("Add Student");
        JButton listButton = new JButton("List Students");
        JButton clearButton = new JButton("Clear Output");

        buttonPanel.add(addButton);
        buttonPanel.add(listButton);
        buttonPanel.add(clearButton);

        // Bottom - output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // ---- Button actions ----
        addButton.addActionListener(e -> addStudent());
        listButton.addActionListener(e -> listStudents());
        clearButton.addActionListener(e -> outputArea.setText(""));
    }

    private void addStudent() {
        try {
            String name = nameField.getText().trim();
            String ageStr = ageField.getText().trim();
            String email = emailField.getText().trim();
            String course = courseField.getText().trim();

            if (name.isEmpty()) {
                showError("Name cannot be empty");
                return;
            }

            if (course.isEmpty()) {
                showError("Course cannot be empty");
                return;
            }

            Integer age = ageStr.isEmpty() ? null : Integer.parseInt(ageStr);
            email = email.isEmpty() ? null : email;

            // ✅ use your service method exactly like in console Main
            Student s = studentService.addStudent(name, age, email, course);

            outputArea.append("Added: " + s + "\n");

        } catch (NumberFormatException ex) {
            showError("Age must be a number.");
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void listStudents() {
        try {
            List<Student> students = studentService.getAllStudents();

            outputArea.append("---- All Students ----\n");
            if (students.isEmpty()) {
                outputArea.append("No students found.\n");
            } else {
                for (Student s : students) {
                    outputArea.append(s.toString() + "\n");
                }
            }
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
