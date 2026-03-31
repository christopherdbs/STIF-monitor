package fr.efrei.stif.monitor.controller;

import fr.efrei.stif.monitor.model.CompletedReport;
import fr.efrei.stif.monitor.model.IncidentReport;
import fr.efrei.stif.monitor.model.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository){
        this.incidentRepository = incidentRepository;
    }

    public List<CompletedReport> getActiveIncidents(){
        List<IncidentReport> reports = incidentRepository.findByIsRepairedFalse();

        return reports.stream().map(report -> {
            CompletedReport completed = new CompletedReport(report);
            long hours = getElapsedHours(report);
            completed.setElapsedHours(hours);
            completed.setStatusIndicator(getStatusIndicator(report, hours));
            return completed;
        }).toList();
    }

    public List<IncidentReport> getFullHistory(Integer equipmentId) {
        return incidentRepository.findByEquipmentIdOrderByDateTimeDesc(equipmentId);
    }

    public List<IncidentReport> getRecentHistory(Integer equipmentId, Integer reportId) {
        return incidentRepository.findTop3ByEquipmentIdAndIdNotOrderByDateTimeDesc(equipmentId, reportId);
    }

    public IncidentReport save(IncidentReport incident) {
        return incidentRepository.save(incident);
    }

    public IncidentReport findById(Integer id) {
        return incidentRepository.findById(id).orElse(null);
    }


    public long getElapsedHours(IncidentReport report) {
        System.out.print(report.getDateTime());
        if (report.getDateTime() == null) return 0;
        return Duration.between(report.getDateTime(), LocalDateTime.now()).toHours();
    }

    public String getStatusIndicator(IncidentReport report, long hours) {
        if (report.getAssignedCompany() != null) return "ASSIGNED";
        if (hours < 48) return "GREEN";
        if (hours < 72) return "ORANGE";
        return "RED";
    }
    public void delete(Integer id) {
        incidentRepository.deleteById(id);
    }
}
