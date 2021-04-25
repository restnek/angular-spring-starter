import { Component } from '@angular/core';
import { SessionService } from '../core/services/user/session.service';
import { ApiPaths } from '../core/util/api-paths';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  fooUrl = ApiPaths.FOO;
  whoAmIUrl = ApiPaths.WHO_AM_I;
  usersUrl = ApiPaths.USERS;

  constructor(public sessionService: SessionService) {
  }
}
