package com.subham.demo;

import com.subham.demo.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    Optional<Incident> findByTitle(String title);
}