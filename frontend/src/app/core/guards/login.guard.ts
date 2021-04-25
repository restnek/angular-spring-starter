import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { SessionService } from '../services/user/session.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  constructor(private router: Router, private sessionService: SessionService) {
  }

  canActivate(): boolean {
    if (this.sessionService.isSignedIn()) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
}
