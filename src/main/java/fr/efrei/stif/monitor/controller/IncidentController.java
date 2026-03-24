package fr.efrei.stif.monitor.controller;

import fr.efrei.stif.monitor.controller.IncidentService;
import fr.efrei.stif.monitor.model.IncidentReport;
import fr.efrei.stif.monitor.model.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    @GetMapping("/")
    public String dashboard(Model model) {
        List<IncidentReport> activeIncidents = incidentService.getActiveIncidents();
        System.out.println(activeIncidents);
        model.addAttribute("incidents", activeIncidents);
        return "dashboard";
    }

    @GetMapping("/incidents/new")
    public String showCreateForm(Model model) {
        model.addAttribute("incident", new IncidentReport());
        //model.addAttribute("equipments", incidentService.findAll());
        return "create-incident";
    }
}