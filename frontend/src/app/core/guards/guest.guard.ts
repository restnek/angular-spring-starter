import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { SessionService } from '../services/user/session.service';

@Injectable({
  providedIn: 'root'
})
export class GuestGuard implements CanActivate {
  constructor(private router: Router, private sessionService: SessionService) {
  }

  canActivate(): boolean {
    if (this.sessionService.isSignedIn()) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }
}
