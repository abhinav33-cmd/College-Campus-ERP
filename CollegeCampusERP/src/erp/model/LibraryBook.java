package erp.model;

public class LibraryBook implements Displayable {
    private final String isbn;
    private String title;
    private boolean issued;
    private String issuedToStudentId;

    public LibraryBook(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
        this.issued = false;
        this.issuedToStudentId = null;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public boolean isIssued() { return issued; }
    public String getIssuedToStudentId() { return issuedToStudentId; }

    public void issueTo(String studentId) {
        this.issued = true;
        this.issuedToStudentId = studentId;
    }

    public void returnBook() {
        this.issued = false;
        this.issuedToStudentId = null;
    }

    @Override
    public String getSummary() {
        return String.format("[%s] %s | Status: %s",
                isbn, title, issued ? "Issued to " + issuedToStudentId : "Available");
    }
}
