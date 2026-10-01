package com.hcl.VenueVista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.VenueVista.model.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}