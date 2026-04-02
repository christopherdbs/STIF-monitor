package fr.efrei.stif.monitor.controller;

import fr.efrei.stif.monitor.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final EquipmentService equipmentService;

    public IncidentService(IncidentRepository incidentRepository, EquipmentService equipmentService){
        this.incidentRepository = incidentRepository;
        this.equipmentService = equipmentService;
    }

    public List<CompletedReport> getActiveIncidents(){
        List<IncidentReport> reports = incidentRepository.findByStatusFalseOrderByDateTimeAsc();

        return reports.stream().map(report -> {
            CompletedReport completed = new CompletedReport(report);
            long hours = getElapsedHours(report);
            completed.setElapsedHours(hours);
            completed.setStatusIndicator(getStatusIndicator(hours));
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

    public String getStatusIndicator(long hours) {
        if (hours < 48) return "GREEN";
        if (hours < 72) return "ORANGE";
        return "RED";
    }

    public void delete(Integer id) {
        incidentRepository.deleteById(id);
    }

    public List<IncidentReport> findAll() {
        return incidentRepository.findAll();
    }

    public List<Map<String, Object>> getCompanySuggestions() {
        List<String> companyNames = Arrays.asList(
                "Otis Maintenance", "Schindler Service", "Kone Repairs",
                "ThyssenKrupp Tech", "Sigma Elevators", "Stannah Specialist"
        );

        Random random = new Random();
        Collections.shuffle(companyNames);

        return companyNames.stream()
                .limit(3)
                .map(name -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("percentage", random.nextInt(31) + 65);
                    return map;
                })
                .sorted((m1, m2) -> ((Integer) m2.get("percentage")).compareTo((Integer) m1.get("percentage")))
                .collect(Collectors.toList());
    }
}
