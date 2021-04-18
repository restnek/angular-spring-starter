import {Component} from '@angular/core';
import {UserService} from '../../service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  constructor(
    private userService: UserService
  ) {
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }
}
