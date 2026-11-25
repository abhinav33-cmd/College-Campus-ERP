package erp.model;

public abstract class Person implements Displayable {
    private static int idCounter = 1000;
    private final String id;
    private String name;
    private String email;

    public Person(String prefix, String name, String email) {
        this.id = prefix + (++idCounter);
        this.name = name;
        this.email = email;
    }

    public abstract String getRole();

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}
