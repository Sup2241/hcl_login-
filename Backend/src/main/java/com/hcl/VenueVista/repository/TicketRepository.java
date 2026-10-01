package com.hcl.VenueVista.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.VenueVista.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}