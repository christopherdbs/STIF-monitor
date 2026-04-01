package fr.efrei.stif.monitor.controller;

import fr.efrei.stif.monitor.controller.IncidentService;
import fr.efrei.stif.monitor.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        System.out.println(activeIncidents);
        model.addAttribute("incidents", activeIncidents);
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
        System.out.println("Nombre d'équipements envoyés au form : " + equipmentService.findAll().size());
        model.addAttribute("incidentReport", incidentReport);
        model.addAttribute("equipments", equipmentService.findAll());

        return "incident-form";
    }
/*
    @PostMapping("/incidents/{id}/modify")
    public String modifyForm(@RequestParam Integer id, @ModelAttribute IncidentReport oldIncidentReport, Model model) {
        incidentService.updateIncidentReport(id, oldIncidentReport);
        return "redirect:/incidents/{id}";
    }
*/


}