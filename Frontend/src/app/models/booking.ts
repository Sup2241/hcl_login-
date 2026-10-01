import { User } from './user';
import { Event } from './event';
import { Ticket } from './ticket';

export interface Booking {
  id: number;
  user: User;
  event: Event;
  ticket: Ticket;
  quantity: number;
  totalAmount: number;
  status: string;
  seatNumbers: string[];
}
