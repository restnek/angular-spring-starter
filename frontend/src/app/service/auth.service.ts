import {Injectable} from '@angular/core';
import {HttpHeaders, HttpParams} from '@angular/common/http';
import {ApiService} from './api.service';
import {UrlService} from './url.service';
import {map} from 'rxjs/operators';

interface LoginRequest {
  username: string,
  password: string
}

interface SignUpRequest {
  username: string,
  password: string,
  firstname: string,
  lastname: string
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  currentUser;

  constructor(
    private apiService: ApiService,
    private urls: UrlService
  ) {
  }

  isSignedIn() {
    return !!this.currentUser;
  }

  login(user: LoginRequest) {
    const headers = new HttpHeaders({
      Accept: 'application/json',
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    const body = new HttpParams()
      .set('username', user.username)
      .set('password', user.password);

    return this.apiService.post(this.urls.auth.login, body, headers)
      .pipe(map(res => {
        this.currentUser = res.body;
      }))
  }

  signup(user: SignUpRequest) {
    return this.apiService.post(this.urls.auth.signUp, JSON.stringify(user))
      .pipe(map(res => {
        this.currentUser = res.body;
      }));
  }

  whoAmI() {
    return this.apiService.get(this.urls.auth.whoAmI)
      .pipe(map(res => {
        this.currentUser = res.body;
      }));
  }

  logout() {
    return this.apiService.post(this.urls.auth.logout)
      .pipe(map(() => {
        this.currentUser = null;
      }));
  }
}
