package erp.concurrency;

import erp.service.ReportGenerator;

public class ReportExportThread extends Thread {
    private final ReportGenerator reportGenerator;
    private String generatedReport;

    public ReportExportThread(ReportGenerator reportGenerator) {
        super("ERP-Report-Worker");
        this.reportGenerator = reportGenerator;
    }

    @Override
    public void run() {
        System.out.println("[Thread: " + getName() + "] State during run(): " + getState());
        try {
            Thread.sleep(800);
            generatedReport = reportGenerator.generateCampusSummaryReport();
        } catch (InterruptedException e) {
            System.out.println("[Thread: " + getName() + "] Report generation interrupted.");
        }
    }

    public String getGeneratedReport() {
        return generatedReport;
    }
}
