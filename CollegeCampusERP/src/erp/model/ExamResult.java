package erp.model;

public class ExamResult implements Displayable {
    private final String studentId;
    private final int[][] marksMatrix;
    private final String[] subjects;

    public ExamResult(String studentId, String[] subjects, int[][] marksMatrix) {
        this.studentId = studentId;
        this.subjects = subjects;
        this.marksMatrix = marksMatrix;
    }

    public String getStudentId() { return studentId; }

    public double calculateOverallPercentage() {
        int total = 0;
        int count = 0;
        for (int i = 0; i < marksMatrix.length; i++) {
            for (int j = 0; j < marksMatrix[i].length; j++) {
                total += marksMatrix[i][j];
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) total / count;
    }

    @Override
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Exam Matrix for ").append(studentId).append(":\n");
        for (int i = 0; i < subjects.length; i++) {
            sb.append(String.format("  %-10s -> ST1: %d, ST2: %d, Final: %d\n",
                    subjects[i], marksMatrix[i][0], marksMatrix[i][1], marksMatrix[i][2]));
        }
        sb.append(String.format("  Overall Average: %.2f%%", calculateOverallPercentage()));
        return sb.toString();
    }
}
