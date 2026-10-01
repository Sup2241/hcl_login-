import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Event } from '../models/event';
import { Venue } from '../models/venue';
import { Employee } from '../models/employee';
import { Ticket } from '../models/ticket';
import { Booking } from '../models/booking';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // EVENTS
  getEvents(): Observable<Event[]> {
    return this.http.get<Event[]>(`${this.baseUrl}/events`);
  }

  // VENUES
  getVenues(): Observable<Venue[]> {
    return this.http.get<Venue[]>(`${this.baseUrl}/venues`);
  }

  // EMPLOYEES
  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.baseUrl}/employees`);
  }

  // TICKETS
  getTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.baseUrl}/tickets`);
  }

  // BOOKINGS
  getBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.baseUrl}/bookings`);
  }

  createBooking(booking: any): Observable<Booking> {
    return this.http.post<Booking>(`${this.baseUrl}/bookings`, booking);
  }

  cancelBooking(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/bookings/${id}/cancel`, {});
  }
}
