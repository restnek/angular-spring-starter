import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {UrlService} from './url.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(
    private apiService: ApiService,
    private urls: UrlService
  ) {
  }

  getAll() {
    return this.apiService.get(this.urls.users);
  }

  getById(id: number) {
    const path = this.urls.resolve(this.urls.users, id);
    return this.apiService.get(path);
  }
}
