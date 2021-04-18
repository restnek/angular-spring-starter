import {Component} from '@angular/core';
import {AuthService} from '../../../service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-account-menu',
  templateUrl: './account-menu.component.html',
  styleUrls: ['./account-menu.component.scss']
})
export class AccountMenuComponent {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  logout() {
    this.authService.logout().subscribe(res => {
      this.router.navigate(['/login']);
    });
  }
}
