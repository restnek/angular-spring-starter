import { Authority } from './authority.model';

export interface User {
  id: number;
  username: string;
  firstname: string;
  lastname: string;
  authorities: Authority[];
}
