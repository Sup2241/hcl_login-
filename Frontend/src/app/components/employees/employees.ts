import { Component, OnInit, signal } from '@angular/core';
import { ApiService } from '../../services/api';
import { Employee } from '../../models/employee';

@Component({
  selector: 'app-employees',
  standalone: true,
  templateUrl: './employees.html',
  styleUrl: './employees.css',
})
export class Employees implements OnInit {
  employees = signal<Employee[]>([]);

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    console.log('Employees component loaded');

    this.apiService.getEmployees().subscribe({
      next: (data) => {
        console.log('EMPLOYEES FROM BACKEND:', data);

        this.employees.set(data);
      },

      error: (error) => {
        console.error('ERROR LOADING EMPLOYEES:', error);
      },
    });
  }
}
