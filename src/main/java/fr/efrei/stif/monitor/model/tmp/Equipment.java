package fr.efrei.stif.monitor.model.tmp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Equipment {
    private Long id;
    private String serialNumber;
    private String stationName;
    private String type;
    private List<IncidentReport> incidents = new ArrayList<>();


    public Long getId() { return id; }
    public String getSerialNumber() { return serialNumber; }
    public String getStationName() { return stationName; }
    public String getType() { return type; }
    public List<IncidentReport> getIncidents() { return incidents; }

    public void setId(Long id) { this.id = id; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public void setStationName(String stationName) { this.stationName = stationName; }
    public void setType(String type) { this.type = type; }
    public void setIncidents(List<IncidentReport> incidents) { this.incidents = incidents; }
}
