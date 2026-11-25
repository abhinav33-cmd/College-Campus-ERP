package erp.model;

import java.util.HashSet;
import java.util.Set;

public class Student extends Person implements Comparable<Student> {
    private String department;
    private Double cgpa;
    private Double pendingFee;
    private final Set<String> enrolledCourseCodes;

    public Student(String name, String email, String department, Double cgpa, Double pendingFee) {
        super("STU", name, email);
        this.department = department;
        this.cgpa = cgpa;
        this.pendingFee = pendingFee;
        this.enrolledCourseCodes = new HashSet<>();
    }

    @Override
    public String getRole() { return "STUDENT"; }

    public String getDepartment() { return department; }
    public Double getCgpa() { return cgpa; }
    public void setCgpa(Double cgpa) { this.cgpa = cgpa; }
    public Double getPendingFee() { return pendingFee; }
    public void setPendingFee(Double pendingFee) { this.pendingFee = pendingFee; }
    public Set<String> getEnrolledCourseCodes() { return enrolledCourseCodes; }

    public void enrollCourse(String courseCode) {
        enrolledCourseCodes.add(courseCode.toUpperCase());
    }

    @Override
    public int compareTo(Student other) {
        return this.getId().compareTo(other.getId());
    }

    @Override
    public String getSummary() {
        return String.format("[%s] %s | Dept: %s | CGPA: %.2f | Fee Due: Rs.%.2f | Courses: %s",
                getId(), getName(), department, cgpa, pendingFee, enrolledCourseCodes);
    }
}
