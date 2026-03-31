package fr.efrei.stif.monitor.controller;

import fr.efrei.stif.monitor.model.IncidentReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incidents")
public class IncidentAPIController {

    private final IncidentService incidentService;

    public IncidentAPIController(IncidentService incidentService){
        this.incidentService = incidentService;
    }

    @PostMapping
    public ResponseEntity<IncidentReport> create(@RequestBody IncidentReport report) {
        IncidentReport saved = incidentService.save(report);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentReport> update(@PathVariable Integer id, @RequestBody IncidentReport report) {
        IncidentReport updated = incidentService.updateIncidentReport(id, report);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        incidentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
