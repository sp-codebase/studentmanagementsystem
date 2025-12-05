package sms.app;

import sms.repo.StudentRepository;
import sms.service.StudentService;

public class Main {
    private static final StudentService service = new StudentService(new StudentRepository());

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new StudentManagementGUI(service);
        });
    }
}
