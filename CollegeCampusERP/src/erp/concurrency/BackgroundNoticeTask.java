package erp.concurrency;

public class BackgroundNoticeTask implements Runnable {
    @Override
    public void run() {
        String[] notices = {
            "[NOTICE] Semester fee deadline is approaching.",
            "[NOTICE] Mid-Term Exam schedule updated on portal.",
            "[NOTICE] Library books must be renewed within 14 days."
        };
        int idx = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(45000);
                System.out.println("\n" + notices[idx % notices.length]);
                idx++;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
