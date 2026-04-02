package fr.efrei.stif.monitor.controller;

import fr.efrei.stif.monitor.controller.IncidentService;
import fr.efrei.stif.monitor.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IncidentController {

    private final IncidentService incidentService;
    private final EquipmentService equipmentService;

    public IncidentController(IncidentService incidentService, EquipmentService equipmentService){
        this.incidentService = incidentService;
        this.equipmentService = equipmentService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        List<CompletedReport> activeIncidents = incidentService.getActiveIncidents();

        List<IncidentReport> incidents = incidentService.findAll();
        Map<String, Long> stationCountMap = new HashMap<>();
        Map<String, Long> statusCountMap = new HashMap<>();

        for (IncidentReport incidentReport : incidents) {
            String stationName = incidentReport.getEquipment().getStation().getStationName();

            if (stationName != null && !stationName.isEmpty()) {
                if(stationCountMap.containsKey(stationName)){
                    stationCountMap.put(stationName, stationCountMap.get(stationName) + 1);
                }else {
                    stationCountMap.put(stationName, 1L);
                }
            }
        }
        for (IncidentReport incidentReport : incidents) {
            String status = incidentReport.getStatus() ? "Resolved" : "Pending";

            statusCountMap.put(status, statusCountMap.getOrDefault(status, 0L) + 1);
        }

        model.addAttribute("incidents", activeIncidents);
        model.addAttribute("incidentsStationCount", stationCountMap);
        model.addAttribute("incidentsStatusCount", statusCountMap);
        return "dashboard";
    }

    @GetMapping("/incidents/{id}")
    public String details(Model model, @PathVariable Integer id) {
        IncidentReport incidentReport = incidentService.findById(id);
        if(incidentReport != null) {
            model.addAttribute("incidentReport", incidentReport);
            model.addAttribute("history", incidentService.getRecentHistory(incidentReport.getEquipment().getId(), incidentReport.getId()));
        }

        return "incident-detail";
    }

    @GetMapping("/equipment/{equipmentId}/history")
    public String history(Model model, @PathVariable Integer equipmentId) {
        Equipment equipment = equipmentService.findById(equipmentId);
        model.addAttribute("fullHistory", incidentService.getFullHistory(equipmentId));
        model.addAttribute("equipment", equipment);
        return "incident-history";
    }


    @GetMapping({"/incidents/add", "/incidents/edit/{id}"})
    public String showIncidentForm(Model model, @PathVariable(required = false) Integer id) {
        IncidentReport incidentReport;

        if (id != null) {
            incidentReport = incidentService.findById(id);

            if (incidentReport == null) {
                return "redirect:/";
            }
        } else {
            incidentReport = new IncidentReport();
            incidentReport.setDateTime(java.time.LocalDateTime.now());
        }
        model.addAttribute("incidentReport", incidentReport);
        model.addAttribute("equipments", equipmentService.findAll());

        return "incident-form";
    }

}