package com.hcl.VenueVista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.VenueVista.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
