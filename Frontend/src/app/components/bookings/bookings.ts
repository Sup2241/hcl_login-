import { Component, OnInit, signal } from '@angular/core';
import { ApiService } from '../../services/api';
import { Booking } from '../../models/booking';

@Component({
  selector: 'app-bookings',
  standalone: true,
  templateUrl: './bookings.html',
  styleUrl: './bookings.css',
})
export class Bookings implements OnInit {
  bookings = signal<Booking[]>([]);

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings(): void {
    this.apiService.getBookings().subscribe({
      next: (data) => {
        console.log('BOOKINGS FROM BACKEND:', data);
        this.bookings.set(data);
      },
      error: (error) => {
        console.error('ERROR LOADING BOOKINGS:', error);
      },
    });
  }

  cancelBooking(id: number): void {
    this.apiService.cancelBooking(id).subscribe({
      next: (updatedBooking) => {
        console.log('BOOKING CANCELLED:', updatedBooking);

        this.bookings.update((currentBookings) =>
          currentBookings.map((booking) => (booking.id === id ? updatedBooking : booking)),
        );
      },

      error: (error) => {
        console.error('CANCELLATION ERROR:', error);
        alert(error.error?.message || 'Unable to cancel booking. Please try again.');
      },
    });
  }
}
