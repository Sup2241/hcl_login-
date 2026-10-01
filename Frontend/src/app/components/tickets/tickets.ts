import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api';
import { Ticket } from '../../models/ticket';

@Component({
  selector: 'app-tickets',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './tickets.html',
  styleUrl: './tickets.css',
})
export class Tickets implements OnInit {
  tickets = signal<Ticket[]>([]);

  selectedTicket: Ticket | null = null;

  quantity: number = 1;

  seatNumbers: string = '';

  bookingMessage: string = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    console.log('Tickets component loaded');

    this.apiService.getTickets().subscribe({
      next: (data) => {
        console.log('TICKETS FROM BACKEND:', data);

        this.tickets.set(data);
      },

      error: (error) => {
        console.error('ERROR LOADING TICKETS:', error);
      },
    });
  }

  openBooking(ticket: Ticket): void {
    this.selectedTicket = ticket;

    this.quantity = 1;

    this.seatNumbers = '';

    this.bookingMessage = '';
  }

  closeBooking(): void {
    this.selectedTicket = null;

    this.bookingMessage = '';
  }

  getTotal(): number {
    return this.selectedTicket ? this.selectedTicket.price * this.quantity : 0;
  }

  confirmBooking(): void {
    if (!this.selectedTicket) {
      return;
    }

    if (this.quantity < 1) {
      this.bookingMessage = 'Quantity must be at least 1.';

      return;
    }

    if (this.quantity > this.selectedTicket.quantity) {
      this.bookingMessage = 'Not enough tickets available.';

      return;
    }

    const seats = this.seatNumbers
      .split(',')
      .map((seat) => seat.trim().toUpperCase())
      .filter((seat) => seat.length > 0);

    if (seats.length !== this.quantity) {
      this.bookingMessage = `Please enter exactly ${this.quantity} seat number(s).`;

      return;
    }

    const booking = {
      user: {
        id: 1,
      },

      event: {
        id: this.selectedTicket.event.id,
      },

      ticket: {
        id: this.selectedTicket.id,
      },

      quantity: this.quantity,

      totalAmount: this.getTotal(),

      status: 'CONFIRMED',

      seatNumbers: seats,
    };

    console.log('BOOKING SENT TO BACKEND:', booking);

    this.apiService.createBooking(booking).subscribe({
      next: (response) => {
        console.log('BOOKING CREATED:', response);

        this.bookingMessage = 'Booking confirmed successfully!';

        const updatedTickets = this.tickets().map((ticket) => {
          if (ticket.id === this.selectedTicket!.id) {
            return {
              ...ticket,
              quantity: ticket.quantity - this.quantity,
            };
          }

          return ticket;
        });

        this.tickets.set(updatedTickets);

        setTimeout(() => {
          this.closeBooking();
        }, 1500);
      },

      error: (error) => {
        console.error('BOOKING ERROR:', error);

        if (error.error?.message) {
          this.bookingMessage = error.error.message;
        } else {
          this.bookingMessage = 'Booking failed. Please try again.';
        }
      },
    });
  }
}
