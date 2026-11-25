package erp.repository;

import erp.model.*;
import java.util.*;

public class InMemoryERPStore implements DataRepository {
    private final Map<String, Student> students = new TreeMap<>();
    private final Map<String, Faculty> facultyMap = new LinkedHashMap<>();
    private final Map<String, Course> courses = new HashMap<>();
    private final Map<String, Map<String, Integer>> attendanceRecords = new HashMap<>();
    private final Map<String, ExamResult> examResults = new HashMap<>();
    private final Map<String, LibraryBook> libraryBooks = new HashMap<>();

    @Override
    public void saveStudent(Student s) { students.put(s.getId(), s); }

    @Override
    public Student findStudentById(String id) { return students.get(id); }

    @Override
    public Collection<Student> getAllStudents() { return students.values(); }

    @Override
    public void saveFaculty(Faculty f) { facultyMap.put(f.getId(), f); }

    @Override
    public Collection<Faculty> getAllFaculty() { return facultyMap.values(); }

    @Override
    public void saveCourse(Course c) { courses.put(c.getCourseCode(), c); }

    @Override
    public Course findCourseByCode(String code) { return courses.get(code.toUpperCase()); }

    @Override
    public Collection<Course> getAllCourses() { return courses.values(); }

    @Override
    public void saveAttendance(String studentId, String courseCode, Integer percentage) {
        if (!attendanceRecords.containsKey(studentId)) {
            attendanceRecords.put(studentId, new HashMap<>());
        }
        attendanceRecords.get(studentId).put(courseCode.toUpperCase(), percentage);
    }

    @Override
    public Map<String, Integer> getAttendanceForStudent(String studentId) {
        return attendanceRecords.getOrDefault(studentId, Collections.emptyMap());
    }

    @Override
    public void saveExamResult(ExamResult result) { examResults.put(result.getStudentId(), result); }

    @Override
    public ExamResult getExamResult(String studentId) { return examResults.get(studentId); }

    @Override
    public void saveBook(LibraryBook book) { libraryBooks.put(book.getIsbn(), book); }

    @Override
    public LibraryBook findBookByIsbn(String isbn) { return libraryBooks.get(isbn); }

    @Override
    public Collection<LibraryBook> getAllBooks() { return libraryBooks.values(); }
}
