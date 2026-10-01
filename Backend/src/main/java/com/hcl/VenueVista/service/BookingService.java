package com.hcl.VenueVista.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.VenueVista.model.Booking;
import com.hcl.VenueVista.model.Event;
import com.hcl.VenueVista.model.Ticket;
import com.hcl.VenueVista.model.User;
import com.hcl.VenueVista.repository.BookingRepository;
import com.hcl.VenueVista.repository.EventRepository;
import com.hcl.VenueVista.repository.TicketRepository;
import com.hcl.VenueVista.repository.UserRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public BookingService(
            BookingRepository bookingRepository,
            TicketRepository ticketRepository,
            EventRepository eventRepository,
            UserRepository userRepository) {

        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(long id) {

        return bookingRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));
    }

    @Transactional
    public Booking createBooking(Booking booking) {

        // -------------------------------
        // 1. Check user
        // -------------------------------

        if (booking.getUser() == null) {
            throw new RuntimeException("User information is required");
        }

        User user = userRepository
                .findById(booking.getUser().getId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // -------------------------------
        // 2. Check event
        // -------------------------------

        if (booking.getEvent() == null) {
            throw new RuntimeException("Event information is required");
        }

        Event event = eventRepository
                .findById(booking.getEvent().getId())
                .orElseThrow(() ->
                        new RuntimeException("Event not found"));

        // -------------------------------
        // 3. Check event date
        // -------------------------------

        if (event.getDate() == null) {
            throw new RuntimeException("Event date is not available");
        }

        if (event.getDate().isBefore(LocalDate.now())) {
            throw new RuntimeException(
                    "Cannot book an event that has already passed");
        }

        // -------------------------------
        // 4. Check ticket
        // -------------------------------

        if (booking.getTicket() == null) {
            throw new RuntimeException("Ticket information is required");
        }

        Ticket ticket = ticketRepository
                .findById(booking.getTicket().getId())
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        // -------------------------------
        // 5. Check ticket belongs to event
        // -------------------------------

        if (ticket.getEvent() == null) {
            throw new RuntimeException(
                    "Ticket is not associated with an event");
        }

        if (ticket.getEvent().getId() != event.getId()) {
            throw new RuntimeException(
                    "Selected ticket does not belong to selected event");
        }

        // -------------------------------
        // 6. Check quantity
        // -------------------------------

        if (booking.getQuantity() <= 0) {
            throw new RuntimeException(
                    "Quantity must be at least 1");
        }

        if (booking.getQuantity() > ticket.getQuantity()) {
            throw new RuntimeException(
                    "Not enough tickets available");
        }

        // -------------------------------
        // 7. Check seats
        // -------------------------------

        if (booking.getSeatNumbers() == null ||
                booking.getSeatNumbers().isEmpty()) {

            throw new RuntimeException(
                    "Seat numbers are required");
        }

        if (booking.getSeatNumbers().size()
                != booking.getQuantity()) {

            throw new RuntimeException(
                    "Number of seats must match quantity");
        }

        // -------------------------------
        // 8. Clean seat numbers
        // -------------------------------

        List<String> seats = booking.getSeatNumbers()
                .stream()
                .map(String::trim)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        booking.setSeatNumbers(seats);

        // -------------------------------
        // 9. Check duplicate seats
        // -------------------------------

        Set<String> uniqueSeats = new HashSet<>(seats);

        if (uniqueSeats.size() != seats.size()) {

            throw new RuntimeException(
                    "Duplicate seat numbers are not allowed");
        }

        // -------------------------------
        // 10. Check already booked seats
        // -------------------------------

        List<Booking> existingBookings =
                bookingRepository.findAll();

        for (Booking existingBooking : existingBookings) {

            if (existingBooking.getEvent() != null &&
                existingBooking.getEvent().getId()
                        == event.getId() &&
                !"CANCELLED".equalsIgnoreCase(
                        existingBooking.getStatus()) &&
                existingBooking.getSeatNumbers() != null) {

                for (String seat : seats) {

                    if (existingBooking
                            .getSeatNumbers()
                            .contains(seat)) {

                        throw new RuntimeException(
                                "Seat " + seat
                                + " is already booked");
                    }
                }
            }
        }

        // -------------------------------
        // 11. Calculate total amount
        // -------------------------------

        double totalAmount =
                ticket.getPrice() * booking.getQuantity();

        booking.setTotalAmount(totalAmount);

        // -------------------------------
        // 12. Set booking status
        // -------------------------------

        booking.setStatus("CONFIRMED");

        // -------------------------------
        // 13. Attach real database objects
        // -------------------------------

        booking.setUser(user);
        booking.setEvent(event);
        booking.setTicket(ticket);

        // -------------------------------
        // 14. Reduce ticket quantity
        // -------------------------------

        ticket.setQuantity(
                ticket.getQuantity()
                        - booking.getQuantity());

        ticketRepository.save(ticket);

        // -------------------------------
        // 15. Save booking
        // -------------------------------

        return bookingRepository.save(booking);
    }

    public Booking updateBooking(
            Long id,
            Booking booking) {

        Booking existingBooking =
                bookingRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Booking not found"));

        /*
         * For safety, we don't allow quantity,
         * ticket or seats to be changed directly
         * through PUT because that could bypass
         * ticket availability and seat validation.
         */

        if (booking.getStatus() != null) {
            existingBooking.setStatus(
                    booking.getStatus());
        }

        return bookingRepository.save(existingBooking);
    }

    @Transactional
    public Booking cancelBooking(long id) {

        Booking booking =
                bookingRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Booking not found"));

        // -------------------------------
        // Already cancelled?
        // -------------------------------

        if ("CANCELLED".equalsIgnoreCase(
                booking.getStatus())) {

            throw new RuntimeException(
                    "Booking is already cancelled");
        }

        // -------------------------------
        // Find ticket
        // -------------------------------

        if (booking.getTicket() == null) {
            throw new RuntimeException(
                    "Ticket information is missing");
        }

        Ticket ticket =
                ticketRepository
                        .findById(
                                booking.getTicket().getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Ticket not found"));

        // -------------------------------
        // Restore tickets
        // -------------------------------

        ticket.setQuantity(
                ticket.getQuantity()
                        + booking.getQuantity());

        ticketRepository.save(ticket);

        // -------------------------------
        // Cancel booking
        // -------------------------------

        booking.setStatus("CANCELLED");

        return bookingRepository.save(booking);
    }

    @Transactional
    public void deleteBooking(long id) {

        Booking booking =
                bookingRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Booking not found"));

        /*
         * If booking is still confirmed,
         * restore the ticket quantity before
         * deleting it.
         */

        if (!"CANCELLED".equalsIgnoreCase(
                booking.getStatus())) {

            if (booking.getTicket() != null) {

                Ticket ticket =
                        ticketRepository
                                .findById(
                                        booking.getTicket().getId())
                                .orElseThrow(() ->
                                        new RuntimeException(
                                                "Ticket not found"));

                ticket.setQuantity(
                        ticket.getQuantity()
                                + booking.getQuantity());

                ticketRepository.save(ticket);
            }
        }

        bookingRepository.deleteById(id);
    }
}