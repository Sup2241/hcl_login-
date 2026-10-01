package com.hcl.VenueVista.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hcl.VenueVista.model.Venue;
import com.hcl.VenueVista.repository.VenueRepository;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    // Get all venues
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    // Get venue by ID
    public Venue getVenueById(long id) {
        return venueRepository.findById(id).orElse(null);
    }

    // Create a new venue
    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    // Update an existing venue
    public Venue updateVenue(Long id, Venue venue) {
        venue.setId(id);
        return venueRepository.save(venue);
    }

    // Delete a venue
    public void deleteVenue(long id) {
        venueRepository.deleteById(id);
    }
}