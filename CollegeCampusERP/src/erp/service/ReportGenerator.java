package erp.service;

import erp.model.*;
import erp.repository.DataRepository;
import java.util.*;

public class ReportGenerator {
    private final DataRepository repo;

    public ReportGenerator(DataRepository repo) {
        this.repo = repo;
    }

    public String generateCampusSummaryReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================ CAMPUS ERP SUMMARY REPORT ================\n");
        sb.append("Total Students: ").append(repo.getAllStudents().size()).append("\n");
        sb.append("Total Faculty : ").append(repo.getAllFaculty().size()).append("\n");
        sb.append("Total Courses : ").append(repo.getAllCourses().size()).append("\n");

        sb.append("\n--- Registered Students (Iterator Traversal) ---\n");
        Iterator<Student> it = repo.getAllStudents().iterator();
        while (it.hasNext()) {
            sb.append("  * ").append(it.next().getSummary()).append("\n");
        }
        sb.append("===========================================================\n");
        return sb.toString();
    }
}
