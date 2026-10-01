package com.hcl.VenueVista.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hcl.VenueVista.model.Event;
import com.hcl.VenueVista.repository.EventRepository;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get event by ID
    public Event getEventById(long id) {
        return eventRepository.findById(id).orElse(null);
    }

    // Create a new event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // Update an existing event
    public Event updateEvent(Long id, Event event) {
        event.setId(id);
        return eventRepository.save(event);
    }

    // Delete an event
    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }
}