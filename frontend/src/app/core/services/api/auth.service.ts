import { HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ChangePasswordRequest } from '../../models/requests/change-password-request.model';
import { LoginRequest } from '../../models/requests/login-request.model';
import { SignupRequest } from '../../models/requests/signup-request.model';
import { ApiPaths } from '../../util/api-paths';
import { SessionService } from '../user/session.service';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(
    private apiService: ApiService,
    private sessionService: SessionService
  ) {
  }

  login(body: LoginRequest): Observable<any> {
    const headers = new HttpHeaders({
      Accept: 'application/json',
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    const bodyParams = new HttpParams()
      .set('username', body.username)
      .set('password', body.password);

    return this.apiService.post(ApiPaths.LOGIN, bodyParams, headers)
      .pipe(tap(res => this.sessionService.updateSession(res)));
  }

  signup(body: SignupRequest): Observable<any> {
    return this.apiService.post(ApiPaths.SIGN_UP, JSON.stringify(body))
      .pipe(tap(res => this.sessionService.updateSession(res)));
  }

  whoAmI(): Observable<any> {
    return this.apiService.get(ApiPaths.WHO_AM_I);
  }

  logout(): Observable<any> {
    this.sessionService.clearSession();
    return this.apiService.post(ApiPaths.LOGOUT);
  }

  changePassword(body: ChangePasswordRequest): Observable<any> {
    return this.apiService.post(ApiPaths.CHANGE_PASSWORD, JSON.stringify(body));
  }
}
