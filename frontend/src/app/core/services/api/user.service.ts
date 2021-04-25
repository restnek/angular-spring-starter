import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiPaths } from '../../util/api-paths';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private apiService: ApiService) {
  }

  getAll(): Observable<any> {
    return this.apiService.get(ApiPaths.USERS);
  }

  getById(id: number): Observable<any> {
    const path = ApiPaths.resolve(ApiPaths.USERS, id);
    return this.apiService.get(path);
  }
}
