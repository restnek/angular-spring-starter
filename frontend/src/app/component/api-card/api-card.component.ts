import {Component, Input} from '@angular/core';
import {ApiService} from '../../service';

interface ApiResponse {
  path: string,
  result: string,
  status: number,
  statusText: string,
  body: object
}

@Component({
  selector: 'app-api-card',
  templateUrl: './api-card.component.html',
  styleUrls: ['./api-card.component.scss']
})
export class ApiCardComponent {
  expand = false;
  response: ApiResponse;

  @Input() title: string;
  @Input() subTitle: string;
  @Input() imgUrl: string;
  @Input() content: string;
  @Input() path: string;
  @Input() secure: boolean;

  constructor(private apiService: ApiService) {
  }

  onButtonClick(): void {
    this.expand = true;
    setTimeout(() => this.callApi(), 1000);
  }

  callApi(): void {
    this.apiService.get(this.path)
      .subscribe(
        result => this.forgeSuccessResponse(result),
        error => this.forgeErrorResponse(error));
  }

  forgeSuccessResponse(res): void {
    this.response = {
      path: this.path,
      result: 'success',
      status: res.status,
      statusText: res.statusText,
      body: res.body
    }
  }

  forgeErrorResponse(err) {
    this.response = {
      path: this.path,
      result: 'error',
      status: err.status,
      statusText: err.statusText,
      body: err.error
    }
  }
}
