package fr.efrei.stif.monitor.model;

import fr.efrei.stif.monitor.model.IncidentReport;

public class CompletedReport {
    private final IncidentReport report;
    private long elapsedHours;
    private String statusIndicator;

    public CompletedReport(IncidentReport report) {
        this.report = report;
    }

    public IncidentReport getReport() { return report; }
    public long getElapsedHours() { return elapsedHours; }
    public String getStatusIndicator() { return statusIndicator; }

    public void setElapsedHours(long elapsedHours) {
        this.elapsedHours = elapsedHours;
    }

    public void setStatusIndicator(String statusIndicator) {
        this.statusIndicator = statusIndicator;
    }

}
