import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { ApiService } from '../core/services/api/api.service';
import { ApiResponse } from './api-response.model';

@Component({
  selector: 'app-api-card',
  templateUrl: './api-card.component.html',
  styleUrls: ['./api-card.component.scss']
})
export class ApiCardComponent {
  response: ApiResponse;
  expand = false;

  @Input() title: string;
  @Input() subtitle: string;
  @Input() webpUrl: string;
  @Input() fallbackImgUrl: string;
  @Input() description: string;
  @Input() path: string;
  @Input() available: boolean;

  constructor(private apiService: ApiService) {
  }

  onButtonClick(): void {
    this.response = null;
    this.expand = true;
    setTimeout(() => this.callApi(), 1000);
  }

  private callApi(): void {
    this.apiService.get(this.path)
      .subscribe(
        result => this.forgeSuccessResponse(result),
        error => this.forgeErrorResponse(error));
  }

  private forgeSuccessResponse(result: HttpResponse<any>): void {
    this.response = {
      path: this.path,
      result: 'success',
      status: result.status,
      statusText: result.statusText,
      body: result.body
    };
  }

  private forgeErrorResponse(error: HttpErrorResponse): void {
    this.response = {
      path: this.path,
      result: 'error',
      status: error.status,
      statusText: error.statusText,
      body: error.error
    };
  }
}
