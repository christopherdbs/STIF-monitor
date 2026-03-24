package fr.efrei.stif.monitor.model.tmp;

import java.time.Duration;
import java.time.LocalDateTime;

public class IncidentReport {
    private Integer id;
    private String nature;
    private String comments;
    private LocalDateTime reportDateTime;
    private String assignedCompany;
    private boolean isRepaired = false;
    private Equipment equipment;

    public long getElapsedHours() {
        if (reportDateTime == null) return 0;
        return Duration.between(reportDateTime, LocalDateTime.now()).toHours();
    }

    public String getStatusIndicator() {
        if (assignedCompany != null) return "ASSIGNED";

        long hours = getElapsedHours();
        if (hours < 48) return "GREEN";
        if (hours < 72) return "ORANGE";
        return "RED";
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNature() { return nature; }
    public void setNature(String nature) { this.nature = nature; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public LocalDateTime getReportDateTime() { return reportDateTime; }
    public void setReportDateTime(LocalDateTime reportDateTime) { this.reportDateTime = reportDateTime; }

    public String getAssignedCompany() { return assignedCompany; }
    public void setAssignedCompany(String assignedCompany) { this.assignedCompany = assignedCompany; }

    public boolean isRepaired() { return isRepaired; }
    public void setRepaired(boolean repaired) { isRepaired = repaired; }

    public Equipment getEquipment() { return equipment; }
    public void setEquipment(Equipment equipment) { this.equipment = equipment; }
}
