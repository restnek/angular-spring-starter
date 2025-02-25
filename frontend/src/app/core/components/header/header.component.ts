import { Component } from '@angular/core';
import { SessionService } from '../../services/user/session.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  constructor(public sessionService: SessionService) {
  }
}
