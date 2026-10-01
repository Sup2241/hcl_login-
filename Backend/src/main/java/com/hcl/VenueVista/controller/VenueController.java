package com.hcl.VenueVista.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.VenueVista.model.Venue;
import com.hcl.VenueVista.service.VenueService;

@RestController
@RequestMapping("/venues")
@CrossOrigin(origins = "http://localhost:4200")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    // Get all venues
    @GetMapping
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    // Get venue by ID
    @GetMapping("/{id}")
    public Venue getVenueById(@PathVariable long id) {
        return venueService.getVenueById(id);
    }

    // Create venue
    @PostMapping
    public Venue createVenue(@RequestBody Venue venue) {
        return venueService.createVenue(venue);
    }

    // Update venue
    @PutMapping("/{id}")
    public Venue updateVenue(
            @PathVariable long id,
            @RequestBody Venue venue) {

        return venueService.updateVenue(id, venue);
    }

    // Delete venue
    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable long id) {
        venueService.deleteVenue(id);
    }
}