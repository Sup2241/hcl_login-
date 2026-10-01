import { Component, OnInit, signal } from '@angular/core';
import { ApiService } from '../../services/api';
import { Venue } from '../../models/venue';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-venues',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './venues.html',
  styleUrl: './venues.css',
})
export class Venues implements OnInit {
  venues = signal<Venue[]>([]);

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    console.log('Venues component loaded');

    this.apiService.getVenues().subscribe({
      next: (data) => {
        console.log('VENUES FROM BACKEND:', data);
        this.venues.set(data);
      },

      error: (error) => {
        console.error('ERROR LOADING VENUES:', error);
      },
    });
  }
}
