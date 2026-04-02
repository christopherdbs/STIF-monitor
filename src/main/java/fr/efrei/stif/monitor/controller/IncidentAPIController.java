package fr.efrei.stif.monitor.controller;

import fr.efrei.stif.monitor.model.IncidentReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @ResponseBody
    public ResponseEntity<?> deleteIncident(@PathVariable Integer id) {
        try {
            incidentService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/assign")
    public ResponseEntity<List<Map<String, Object>>> getSuggestions(@PathVariable Integer id) {
        return ResponseEntity.ok(incidentService.getCompanySuggestions());
    }
}
