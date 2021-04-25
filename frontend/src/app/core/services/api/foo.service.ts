import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiPaths } from '../../util/api-paths';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class FooService {
  constructor(private apiService: ApiService) {
  }

  getFoo(): Observable<any> {
    return this.apiService.get(ApiPaths.FOO);
  }
}
