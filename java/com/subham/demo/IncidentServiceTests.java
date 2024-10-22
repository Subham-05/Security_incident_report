package com.subham.demo;

import com.subham.demo.Incident;
import com.subham.demo.IncidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class IncidentServiceTest {

    @InjectMocks
    private IncidentService incidentService;

    @Mock
    private IncidentRepository incidentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateIncidentValid() {
        Incident incident = new Incident();
        incident.setTitle("Test Incident");
        incident.setDescription("Test Description");
        incident.setSeverity("Medium");
        incident.setIncidentDate(LocalDate.now());

        // Mock repository behavior
        when(incidentRepository.existsByTitle(any())).thenReturn(false);

        Incident createdIncident = incidentService.createIncident(incident);
        assertNotNull(createdIncident);
        assertEquals(incident.getTitle(), createdIncident.getTitle());
    }

    @Test
    public void testCreateIncidentWithPastDate() {
        Incident incident = new Incident();
        incident.setTitle("Test Incident");
        incident.setDescription("Test Description");
        incident.setSeverity("Medium");
        incident.setIncidentDate(LocalDate.now().minusDays(31));

        assertThrows(IllegalArgumentException.class, () -> {
            incidentService.createIncident(incident);
        });
    }
}