import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';
import { ApiService } from '../../services/api';
import { Event } from '../../models/event';

@Component({
  selector: 'app-events',
  standalone: true,
  imports: [RouterLink, DatePipe],
  templateUrl: './events.html',
  styleUrl: './events.css',
})
export class Events implements OnInit {
  events = signal<Event[]>([]);

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.getEvents().subscribe({
      next: (data) => {
        console.log('EVENTS FROM BACKEND:', data);
        this.events.set(data);
      },
      error: (error) => {
        console.error('ERROR LOADING EVENTS:', error);
      },
    });
  }
}
