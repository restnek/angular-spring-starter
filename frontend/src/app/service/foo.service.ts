import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {UrlService} from './url.service';

@Injectable({
  providedIn: 'root'
})
export class FooService {
  constructor(
    private apiService: ApiService,
    private urls: UrlService
  ) {
  }

  getFoo() {
    return this.apiService.get(this.urls.foo);
  }
}
