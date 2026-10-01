import { Event } from './event';

export interface Ticket {
  id: number;
  event: Event;
  ticketType: string;
  price: number;
  quantity: number;
}
