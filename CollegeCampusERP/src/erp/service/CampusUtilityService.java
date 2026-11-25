package erp.service;

import erp.exception.ERPException;
import erp.exception.ResourceNotFoundException;
import erp.model.LibraryBook;
import erp.model.Student;
import erp.repository.DataRepository;
import java.util.*;

public class CampusUtilityService {
    private final DataRepository repo;
    private final Deque<String> libraryActivityLog = new ArrayDeque<>();

    public CampusUtilityService(DataRepository repo) {
        this.repo = repo;
    }

    public void payFee(String studentId, double amount) throws ERPException {
        Student s = repo.findStudentById(studentId);
        if (s == null) throw new ResourceNotFoundException("Student not found: " + studentId);
        if (amount <= 0 || amount > s.getPendingFee()) {
            throw new ERPException("Invalid payment amount. Current Due: Rs." + s.getPendingFee());
        }
        s.setPendingFee(s.getPendingFee() - amount);
    }

    public PriorityQueue<Student> getFeeDefaultersQueue() {
        PriorityQueue<Student> pq = new PriorityQueue<>(new Comparator<Student>() {
            @Override
            public int compare(Student a, Student b) {
                return Double.compare(b.getPendingFee(), a.getPendingFee());
            }
        });
        for (Student s : repo.getAllStudents()) {
            if (s.getPendingFee() > 0) {
                pq.offer(s);
            }
        }
        return pq;
    }

    public void issueBook(String isbn, String studentId) throws ERPException {
        LibraryBook book = repo.findBookByIsbn(isbn);
        if (book == null) throw new ResourceNotFoundException("Book not found: " + isbn);
        if (book.isIssued()) throw new ERPException("Book is already issued to " + book.getIssuedToStudentId());
        if (repo.findStudentById(studentId) == null) throw new ResourceNotFoundException("Student not found: " + studentId);

        book.issueTo(studentId);
        libraryActivityLog.addFirst("ISSUED " + isbn + " to " + studentId);
    }

    public void returnBook(String isbn) throws ERPException {
        LibraryBook book = repo.findBookByIsbn(isbn);
        if (book == null) throw new ResourceNotFoundException("Book not found: " + isbn);
        if (!book.isIssued()) throw new ERPException("Book is not currently issued.");

        libraryActivityLog.addFirst("RETURNED " + isbn + " from " + book.getIssuedToStudentId());
        book.returnBook();
    }

    public Deque<String> getLibraryActivityLog() {
        return libraryActivityLog;
    }
}
