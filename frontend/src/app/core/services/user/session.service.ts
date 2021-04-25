import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../models/session/user.model';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private readonly authHeader = 'Authorization';
  private readonly tokenType = 'Bearer';
  private readonly adminRole = 'ROLE_ADMIN';

  private readonly tokenExpirationKey = 'expiration';
  private readonly currentUserKey = 'user';

  get tokenExpiration(): number {
    return +localStorage.getItem(this.tokenExpirationKey);
  }

  set tokenExpiration(expiration: number) {
    localStorage.setItem(this.tokenExpirationKey, expiration.toString());
  }

  get currentUser(): User | null {
    return JSON.parse(localStorage.getItem(this.currentUserKey));
  }

  set currentUser(user: User) {
    localStorage.setItem(this.currentUserKey, JSON.stringify(user));
  }

  clearSession(): void {
    localStorage.removeItem(this.tokenExpirationKey);
    localStorage.removeItem(this.currentUserKey);
  }

  updateSession(response: HttpResponse<User>): void {
    const auth = response.headers.get(this.authHeader);
    const [type, token] = auth.split(/\s+/);

    if (type === this.tokenType) {
      this.updateExpirationTime(token);
      this.updateCurrentUser(response.body);
    }
  }

  private updateExpirationTime(token: string): void {
    const payloadBase64 = token.split('.')[1];
    const payload = JSON.parse(atob(payloadBase64));
    this.tokenExpiration = payload.exp;
  }

  private updateCurrentUser(user: User): void {
    this.currentUser = user;
  }

  isSignedIn(): boolean {
    const seconds = new Date().getTime() / 1000;
    return seconds < this.tokenExpiration;
  }

  isAdmin(): boolean {
    return this.currentUser?.authorities
      ?.map(a => a.authority)
      .includes(this.adminRole) ?? false;
  }
}
