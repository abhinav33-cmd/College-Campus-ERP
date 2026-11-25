package erp.service;

import erp.exception.InvalidInputException;
import erp.exception.ResourceNotFoundException;
import erp.model.*;
import erp.repository.DataRepository;
import java.util.*;

public class AcademicService {
    private final DataRepository repo;

    public AcademicService(DataRepository repo) {
        this.repo = repo;
    }

    public Student registerStudent(String name, String email, String dept, double cgpa, double fee) {
        if (cgpa < 0.0 || cgpa > 10.0) {
            throw new InvalidInputException("CGPA must be between 0.0 and 10.0.");
        }
        Student s = new Student(name, email, dept, cgpa, fee);
        repo.saveStudent(s);
        return s;
    }

    public Faculty registerFaculty(String name, String email, String dept, String spec) {
        Faculty f = new Faculty(name, email, dept, spec);
        repo.saveFaculty(f);
        return f;
    }

    public Course addCourse(String code, String title, int credits, String facultyId) {
        Course c = new Course(code, title, credits, facultyId);
        repo.saveCourse(c);
        return c;
    }

    public void enrollStudentInCourse(String studentId, String courseCode) throws ResourceNotFoundException {
        Student s = repo.findStudentById(studentId);
        if (s == null) throw new ResourceNotFoundException("Student not found: " + studentId);
        Course c = repo.findCourseByCode(courseCode);
        if (c == null) throw new ResourceNotFoundException("Course not found: " + courseCode);
        s.enrollCourse(courseCode);
    }

    public void markAttendance(String studentId, String courseCode, int percentage) throws ResourceNotFoundException {
        if (repo.findStudentById(studentId) == null) {
            throw new ResourceNotFoundException("Student not found: " + studentId);
        }
        if (percentage < 0 || percentage > 100) {
            throw new InvalidInputException("Attendance percentage must be 0-100.");
        }
        repo.saveAttendance(studentId, courseCode, percentage);
    }

    public void recordExamResult(String studentId, String[] subjects, int[][] marks) throws ResourceNotFoundException {
        if (repo.findStudentById(studentId) == null) {
            throw new ResourceNotFoundException("Student not found: " + studentId);
        }
        repo.saveExamResult(new ExamResult(studentId, subjects, marks));
    }

    public List<Student> getStudentsRankedByCgpa() {
        List<Student> list = new ArrayList<>(repo.getAllStudents());
        Collections.sort(list, new StudentCgpaComparator());
        return list;
    }
}
