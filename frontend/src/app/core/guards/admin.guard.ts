import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { SessionService } from '../services/user/session.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private router: Router, private sessionService: SessionService) {
  }

  canActivate(): boolean {
    if (this.sessionService.isAdmin()) {
      return true;
    }
    this.router.navigate(['/']);
    return false;
  }
}
