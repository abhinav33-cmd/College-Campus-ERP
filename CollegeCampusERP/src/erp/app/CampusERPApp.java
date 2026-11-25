package erp.app;

import erp.concurrency.BackgroundNoticeTask;
import erp.concurrency.ReportExportThread;
import erp.exception.ERPException;
import erp.exception.InvalidInputException;
import erp.model.*;
import erp.repository.DataRepository;
import erp.repository.InMemoryERPStore;
import erp.service.*;

import java.util.*;

public class CampusERPApp {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        DataRepository repo = new InMemoryERPStore();
        AuthService authService = new AuthService();
        AcademicService academicService = new AcademicService(repo);
        CampusUtilityService utilityService = new CampusUtilityService(repo);
        ReportGenerator reportGenerator = new ReportGenerator(repo);

        seedSampleData(repo, academicService);

        Thread noticeThread = new Thread(new BackgroundNoticeTask(), "Campus-Notice-Daemon");
        noticeThread.setDaemon(true);
        noticeThread.setPriority(Thread.MIN_PRIORITY);
        noticeThread.start();

        System.out.println("=== COLLEGE CAMPUS ERP MANAGEMENT SYSTEM (L1-L12) ===");
        boolean running = true;

        while (running) {
            try {
                System.out.print("\nEnter Username (admin/faculty/student) or 'exit': ");
                String user = sc.nextLine();
                if (user.equalsIgnoreCase("exit")) {
                    running = false;
                    continue;
                }
                System.out.print("Enter Password: ");
                String pass = sc.nextLine();

                String role = authService.authenticate(user, pass);
                System.out.println("Login Successful! Role: " + role);

                switch (role) {
                    case "ADMIN":
                        runAdminMenu(academicService, utilityService, reportGenerator, repo);
                        break;
                    case "FACULTY":
                        runFacultyMenu(academicService, repo);
                        break;
                    case "STUDENT":
                        runStudentMenu(utilityService, repo);
                        break;
                }
            } catch (InvalidInputException | NumberFormatException e) {
                System.out.println("[INPUT ERROR] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[SYSTEM ERROR] " + e.getMessage());
            }
        }

        noticeThread.interrupt();
        System.out.println("ERP System Shut Down. Goodbye!");
    }

    private static void runAdminMenu(AcademicService academic, CampusUtilityService utility,
                                     ReportGenerator reports, DataRepository repo) {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Register Student");
            System.out.println("2. Register Faculty");
            System.out.println("3. Add Course");
            System.out.println("4. View Students Ranked by CGPA (Comparator)");
            System.out.println("5. View Fee Defaulters (PriorityQueue)");
            System.out.println("6. Generate Async Campus Report (Thread Lifecycle)");
            System.out.println("7. Logout");
            System.out.print("Choice: ");

            try {
                int ch = Integer.parseInt(sc.nextLine());
                switch (ch) {
                    case 1:
                        System.out.print("Name: "); String sName = sc.nextLine();
                        System.out.print("Email: "); String sEmail = sc.nextLine();
                        System.out.print("Dept: "); String sDept = sc.nextLine();
                        System.out.print("CGPA: "); double cgpa = Double.parseDouble(sc.nextLine());
                        System.out.print("Pending Fee: "); double fee = Double.parseDouble(sc.nextLine());
                        Student s = academic.registerStudent(sName, sEmail, sDept, cgpa, fee);
                        System.out.println("Registered: " + s.getSummary());
                        break;
                    case 2:
                        System.out.print("Name: "); String fName = sc.nextLine();
                        System.out.print("Email: "); String fEmail = sc.nextLine();
                        System.out.print("Dept: "); String fDept = sc.nextLine();
                        System.out.print("Specialization: "); String spec = sc.nextLine();
                        Faculty f = academic.registerFaculty(fName, fEmail, fDept, spec);
                        System.out.println("Registered: " + f.getSummary());
                        break;
                    case 3:
                        System.out.print("Course Code: "); String code = sc.nextLine();
                        System.out.print("Title: "); String title = sc.nextLine();
                        System.out.print("Credits: "); int credits = Integer.parseInt(sc.nextLine());
                        System.out.print("Faculty ID: "); String fId = sc.nextLine();
                        Course c = academic.addCourse(code, title, credits, fId);
                        System.out.println("Added: " + c.getSummary());
                        break;
                    case 4:
                        System.out.println("--- Merit List (Sorted by CGPA) ---");
                        for (Student st : academic.getStudentsRankedByCgpa()) {
                            System.out.println(st.getSummary());
                        }
                        break;
                    case 5:
                        PriorityQueue<Student> pq = utility.getFeeDefaultersQueue();
                        System.out.println("--- Fee Defaulters (Highest Due First) ---");
                        while (!pq.isEmpty()) {
                            Student def = pq.poll();
                            System.out.println(def.getId() + " - " + def.getName() + " | Due: Rs." + def.getPendingFee());
                        }
                        break;
                    case 6:
                        ReportExportThread worker = new ReportExportThread(reports);
                        System.out.println("[Lifecycle] Before start(): " + worker.getState());
                        worker.start();
                        worker.join();
                        System.out.println("[Lifecycle] After join(): " + worker.getState());
                        System.out.println(worker.getGeneratedReport());
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }

    private static void runFacultyMenu(AcademicService academic, DataRepository repo) {
        while (true) {
            System.out.println("\n--- FACULTY MENU ---");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Mark Attendance");
            System.out.println("3. Upload Exam Marks (2D Matrix)");
            System.out.println("4. Logout");
            System.out.print("Choice: ");

            try {
                int ch = Integer.parseInt(sc.nextLine());
                switch (ch) {
                    case 1:
                        System.out.print("Student ID: "); String sid = sc.nextLine();
                        System.out.print("Course Code: "); String cid = sc.nextLine();
                        academic.enrollStudentInCourse(sid, cid);
                        System.out.println("Enrollment successful.");
                        break;
                    case 2:
                        System.out.print("Student ID: "); String aSid = sc.nextLine();
                        System.out.print("Course Code: "); String aCid = sc.nextLine();
                        System.out.print("Attendance %: "); int pct = Integer.parseInt(sc.nextLine());
                        academic.markAttendance(aSid, aCid, pct);
                        System.out.println("Attendance recorded.");
                        break;
                    case 3:
                        System.out.print("Student ID: "); String eSid = sc.nextLine();
                        String[] subjects = {"Java", "DBMS"};
                        int[][] marks = new int[2][3];
                        for (int i = 0; i < 2; i++) {
                            System.out.print(subjects[i] + " (ST1 ST2 Final separated by space): ");
                            String[] parts = sc.nextLine().trim().split("\\s+");
                            if (parts.length < 3) {
                                throw new InvalidInputException("Please enter 3 integer marks separated by spaces.");
                            }
                            marks[i][0] = Integer.parseInt(parts[0]);
                            marks[i][1] = Integer.parseInt(parts[1]);
                            marks[i][2] = Integer.parseInt(parts[2]);
                        }
                        academic.recordExamResult(eSid, subjects, marks);
                        System.out.println("Exam matrix saved.");
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (ERPException | InvalidInputException | NumberFormatException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }

    private static void runStudentMenu(CampusUtilityService utility, DataRepository repo) {
        System.out.print("Enter your Student ID (e.g., STU1001): ");
        String sid = sc.nextLine().trim();
        if (repo.findStudentById(sid) == null) {
            System.out.println("Student ID not found.");
            return;
        }

        while (true) {
            System.out.println("\n--- STUDENT MENU (" + sid + ") ---");
            System.out.println("1. View Profile & Attendance");
            System.out.println("2. View Exam Result Matrix");
            System.out.println("3. Pay Pending Fee");
            System.out.println("4. Issue Library Book");
            System.out.println("5. Return Library Book");
            System.out.println("6. Logout");
            System.out.print("Choice: ");

            try {
                int ch = Integer.parseInt(sc.nextLine());
                switch (ch) {
                    case 1:
                        System.out.println(repo.findStudentById(sid).getSummary());
                        System.out.println("Attendance: " + repo.getAttendanceForStudent(sid));
                        break;
                    case 2:
                        ExamResult res = repo.getExamResult(sid);
                        System.out.println(res != null ? res.getSummary() : "No exam results uploaded yet.");
                        break;
                    case 3:
                        System.out.print("Enter Amount to Pay: ");
                        double amt = Double.parseDouble(sc.nextLine());
                        utility.payFee(sid, amt);
                        System.out.println("Payment successful! Remaining Due: Rs." + repo.findStudentById(sid).getPendingFee());
                        break;
                    case 4:
                        System.out.println("Available Books:");
                        for (LibraryBook b : repo.getAllBooks()) System.out.println("  " + b.getSummary());
                        System.out.print("Enter ISBN to issue: ");
                        utility.issueBook(sc.nextLine().trim(), sid);
                        System.out.println("Book issued.");
                        break;
                    case 5:
                        System.out.print("Enter ISBN to return: ");
                        utility.returnBook(sc.nextLine().trim());
                        System.out.println("Book returned.");
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (ERPException | NumberFormatException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }

    private static void seedSampleData(DataRepository repo, AcademicService academic) {
        Student s1 = academic.registerStudent("Aarav Sharma", "aarav@campus.edu", "CSE", 8.9, 15000.0);
        Student s2 = academic.registerStudent("Meera Nair", "meera@campus.edu", "CSE", 9.4, 5000.0);
        Faculty f1 = academic.registerFaculty("R. Verma", "rverma@campus.edu", "CSE", "Java & Systems");
        academic.addCourse("25CSE0202", "Programming in Java", 4, f1.getId());
        s1.enrollCourse("25CSE0202");
        s2.enrollCourse("25CSE0202");
        repo.saveAttendance(s1.getId(), "25CSE0202", 88);
        repo.saveBook(new LibraryBook("ISBN-101", "Java: The Complete Reference"));
        repo.saveBook(new LibraryBook("ISBN-102", "Effective Java"));
    }
}
