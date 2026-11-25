package erp.repository;

import erp.model.*;
import java.util.*;

public interface DataRepository {
    void saveStudent(Student s);
    Student findStudentById(String id);
    Collection<Student> getAllStudents();

    void saveFaculty(Faculty f);
    Collection<Faculty> getAllFaculty();

    void saveCourse(Course c);
    Course findCourseByCode(String code);
    Collection<Course> getAllCourses();

    void saveAttendance(String studentId, String courseCode, Integer percentage);
    Map<String, Integer> getAttendanceForStudent(String studentId);

    void saveExamResult(ExamResult result);
    ExamResult getExamResult(String studentId);

    void saveBook(LibraryBook book);
    LibraryBook findBookByIsbn(String isbn);
    Collection<LibraryBook> getAllBooks();
}
