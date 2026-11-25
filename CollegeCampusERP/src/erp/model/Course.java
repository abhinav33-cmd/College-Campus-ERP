package erp.model;

public class Course implements Displayable {
    private final String courseCode;
    private String title;
    private Integer credits;
    private String facultyId;

    public Course(String courseCode, String title, Integer credits, String facultyId) {
        this.courseCode = courseCode.trim().toUpperCase();
        this.title = title;
        this.credits = credits;
        this.facultyId = facultyId;
    }

    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public Integer getCredits() { return credits; }
    public String getFacultyId() { return facultyId; }

    @Override
    public String getSummary() {
        return String.format("%s - %s (%d Credits) | Faculty: %s",
                courseCode, title, credits, facultyId);
    }
}
