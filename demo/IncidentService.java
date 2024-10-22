package com.subham.demo;

import com.subham.demo.CustomException;
import com.subham.demo.Incident;
import com.subham.demo.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidentService {
    @Autowired
    private IncidentRepository incidentRepository;

    public Incident createIncident(Incident incident) {
        validateIncident(incident);
        return incidentRepository.save(incident);
    }

    public Incident updateIncident(Long id, Incident updatedIncident) {
        // Logic to update incident
        if (!incidentRepository.existsById(id)) {
            throw new CustomException("Incident not found");
        }
        updatedIncident.setId(id);
        validateIncident(updatedIncident);
        return incidentRepository.save(updatedIncident);
    }
    public List<Incident> listIncidents(String severity, LocalDateTime startDate, LocalDateTime endDate) {
        // Implement filtering logic (example not provided for brevity)
        return incidentRepository.findAll();
    }

    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Incident not found"));
    }

    private void validateIncident(Incident incident) {
        // Validate date, severity, and title
        if (incident.getIncidentDate().isAfter(LocalDateTime.now()) ||
                incident.getIncidentDate().isBefore(LocalDateTime.now().minusDays(30))) {
            throw new CustomException("Incident date must be within the last 30 days and not in the future.");
        }

        if (!List.of("Low", "Medium", "High").contains(incident.getSeverityLevel())) {
            throw new CustomException("Invalid severity level.");
        }

        if (incident.getTitle().length() < 10) {
            throw new CustomException("Title must be at least 10 characters long and unique.");
        }

        incidentRepository.findByTitle(incident.getTitle())
                .ifPresent(existing -> { throw new CustomException("Title must be unique."); });
    }
}