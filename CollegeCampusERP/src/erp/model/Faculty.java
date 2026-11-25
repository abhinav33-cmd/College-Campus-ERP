package erp.model;

public class Faculty extends Person {
    private String department;
    private String specialization;

    public Faculty(String name, String email, String department, String specialization) {
        super("FAC", name, email);
        this.department = department;
        this.specialization = specialization;
    }

    @Override
    public String getRole() { return "FACULTY"; }

    public String getDepartment() { return department; }
    public String getSpecialization() { return specialization; }

    @Override
    public String getSummary() {
        return String.format("[%s] Prof. %s | Dept: %s | Spec: %s",
                getId(), getName(), department, specialization);
    }
}
