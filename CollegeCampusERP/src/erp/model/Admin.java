package erp.model;

public class Admin extends Person {
    private String designation;

    public Admin(String name, String email, String designation) {
        super("ADM", name, email);
        this.designation = designation;
    }

    @Override
    public String getRole() { return "ADMIN"; }

    @Override
    public String getSummary() {
        return String.format("[%s] %s (%s)", getId(), getName(), designation);
    }
}
