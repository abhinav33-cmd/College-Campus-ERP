# College Campus ERP Management System (Console-Based Java Application)

Built strictly using **Lectures 1–12** concepts (OOP, Strings/2D Arrays/Wrappers, Custom Exception Handling, Java Collections Framework, Comparable/Comparator, Multithreading, Thread Lifecycle, and Daemon Threads), with a modular architecture ready to extend for Synchronization, JDBC, Lambdas, Stream API, Generics, and File I/O.

## Default Login Credentials
- **Admin:** `admin` / `admin123`
- **Faculty:** `faculty` / `fac123`
- **Student:** `student` / `stu123` (Sample Student IDs: `STU1001`, `STU1002`)

## How to Open & Run

### Option 1: IntelliJ IDEA / Eclipse / VS Code
1. Extract this ZIP archive.
2. Open the `CollegeCampusERP` folder in your IDE (ensure JDK 21 LTS or JDK 17+ is configured).
3. Mark `src` as the Sources Root (IntelliJ does this automatically if opened as a Java project).
4. Run `src/erp/app/CampusERPApp.java`.

### Option 2: Command Line (Terminal / PowerShell)
```bash
cd CollegeCampusERP
mkdir -p out
javac -d out src/erp/model/*.java src/erp/exception/*.java src/erp/repository/*.java src/erp/service/*.java src/erp/concurrency/*.java src/erp/app/*.java
java -cp out erp.app.CampusERPApp
```
