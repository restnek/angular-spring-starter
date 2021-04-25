import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/api/auth.service';
import { SessionService } from '../../../services/user/session.service';
import { UserSettingsService } from '../../../services/user/user-settings.service';

@Component({
  selector: 'app-account-menu',
  templateUrl: './account-menu.component.html',
  styleUrls: ['./account-menu.component.scss']
})
export class AccountMenuComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    public userSettingsService: UserSettingsService,
    public sessionService: SessionService
  ) {
  }

  changeLanguage(language: string): void {
    this.userSettingsService.changeLanguage(language);
  }

  logout(): void {
    this.authService.logout().subscribe(() => {
      this.router.navigate(['/login']);
    });
  }
}
