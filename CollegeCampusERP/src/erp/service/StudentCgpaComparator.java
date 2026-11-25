package erp.service;

import erp.model.Student;
import java.util.Comparator;

public class StudentCgpaComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Double.compare(s2.getCgpa(), s1.getCgpa());
    }
}
