package com.subham.demo;

import com.subham.demo.Incident;
import com.subham.demo.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    @Autowired
    private IncidentService incidentService;

    @PostMapping
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) {
        return ResponseEntity.ok(incidentService.createIncident(incident));
    }

    @GetMapping
    public ResponseEntity<List<Incident>> listIncidents(@RequestParam(required = false) String severity) {
        return ResponseEntity.ok(incidentService.listIncidents(severity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncident(@PathVariable Long id) {
        // Retrieve incident logic
    }
}