package fr.efrei.stif.monitor.controller;

import fr.efrei.stif.monitor.model.IncidentReport;
import fr.efrei.stif.monitor.model.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    public List<IncidentReport> getActiveIncidents() {
        return incidentRepository.findByIsRepairedFalse();
    }

    public IncidentReport save(IncidentReport incident) {
        return incidentRepository.save(incident);
    }

    public IncidentReport findById(Integer id) {
        return incidentRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        incidentRepository.deleteById(id);
    }
}
