import { Routes } from '@angular/router';

import { Home } from './components/home/home';
import { Events } from './components/events/events';
import { Venues } from './components/venues/venues';
import { Tickets } from './components/tickets/tickets';
import { Bookings } from './components/bookings/bookings';
import { Employees } from './components/employees/employees';

export const routes: Routes = [
  {
    path: '',
    component: Home,
  },

  {
    path: 'events',
    component: Events,
  },

  {
    path: 'venues',
    component: Venues,
  },

  {
    path: 'tickets',
    component: Tickets,
  },

  {
    path: 'bookings',
    component: Bookings,
  },

  {
    path: 'employees',
    component: Employees,
  },

  {
    path: '**',
    redirectTo: '',
  },
];
