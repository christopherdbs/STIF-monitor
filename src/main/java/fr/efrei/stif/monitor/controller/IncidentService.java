package fr.efrei.stif.monitor.controller;

import fr.efrei.stif.monitor.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final EquipmentService equipmentService;

    public IncidentService(IncidentRepository incidentRepository, EquipmentService equipmentService){
        this.incidentRepository = incidentRepository;
        this.equipmentService = equipmentService;
    }

    public List<CompletedReport> getActiveIncidents(){
        List<IncidentReport> reports = incidentRepository.findByStatusFalse();

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
        if (incident.getEquipment() != null && incident.getEquipment().getId() != null) {
            Equipment eq = equipmentService.findById(incident.getEquipment().getId());
            incident.setEquipment(eq);
        }

        Integer agentId = (incident.getAgentId() != null )
                ? incident.getAgentId()
                : 1;

        return incidentRepository.save(incident);
    }

    public IncidentReport findById(Integer id) {
        return incidentRepository.findById(id).orElse(null);
    }

    @Transactional
    public IncidentReport updateIncidentReport(Integer id, IncidentReport newIR) {

        IncidentReport oldIR = incidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incident non trouvé"));

        BeanUtils.copyProperties(newIR, oldIR, "id");

        if (newIR.getEquipment() != null) {
            Equipment eq = equipmentService.findById(newIR.getEquipment().getId());
            oldIR.setEquipment(eq);
        }

        return incidentRepository.save(oldIR);
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
