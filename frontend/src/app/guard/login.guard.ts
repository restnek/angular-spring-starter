import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthService} from '../service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {
  }

  canActivate(): boolean {
    if (this.authService.currentUser) {
      return true;
    } else {
      this.router.navigateByUrl('/');
      return false;
    }
  }
}
