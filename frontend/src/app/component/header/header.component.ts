import {Component} from '@angular/core';
import {AuthService} from '../../service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  constructor(
    private authService: AuthService
  ) {
  }

  isSignedIn() {
    return this.authService.isSignedIn();
  }
}
