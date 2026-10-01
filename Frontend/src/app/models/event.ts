import { Venue } from './venue';

export interface Event {
  id: number;
  name: string;
  date: string;
  description: string;
  venue: Venue;
}
