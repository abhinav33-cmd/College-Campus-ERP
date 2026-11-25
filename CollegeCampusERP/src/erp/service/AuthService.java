package erp.service;

import erp.exception.InvalidInputException;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final Map<String, String> credentials = new HashMap<>();
    private final Map<String, String> roles = new HashMap<>();

    public AuthService() {
        credentials.put("admin", "admin123");
        roles.put("admin", "ADMIN");

        credentials.put("faculty", "fac123");
        roles.put("faculty", "FACULTY");

        credentials.put("student", "stu123");
        roles.put("student", "STUDENT");
    }

    public String authenticate(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty()) {
            throw new InvalidInputException("Username and password cannot be empty.");
        }
        String key = username.trim().toLowerCase();
        if (credentials.containsKey(key) && credentials.get(key).equals(password)) {
            return roles.get(key);
        }
        throw new InvalidInputException("Invalid credentials! Try admin/admin123, faculty/fac123, or student/stu123.");
    }
}
