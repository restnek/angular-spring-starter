import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ValidationError } from '../../models/error/validation-error.model';
import { NotificationService } from './notification.service';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {
  private readonly titleKey = 'notification.server';
  private readonly messageKeyPrefix = 'notification.type.';

  constructor(private notificationService: NotificationService) {
  }

  renderServerErrors(form: FormGroup, errorResponse: HttpErrorResponse): void {
    const error = errorResponse.error;
    this.showNotification(error);

    if (error.type === 'validation' || error.type === 'resourceDuplicate') {
      this.renderValidationErrors(form, error.errors);
    }
  }

  private showNotification(error: any): void {
    const type = error?.type ?? 'disconnect';
    const messageKey = this.messageKeyPrefix + type;
    this.notificationService.show(this.titleKey, messageKey);
  }

  private renderValidationErrors(form: FormGroup, errors: ValidationError[]): void {
    errors.forEach(e => {
      form.get(e.field).setErrors({[e.type]: true});
    });
  }

  hasErrors(form: FormGroup, field: string): boolean {
    return this.getFieldErrors(form, field).length > 0;
  }

  getFieldErrors(form: FormGroup, field: string): string[] {
    const control = form.get(field);
    if (control.touched && control.errors) {
      return Object.keys(control.errors);
    }
    return [];
  }
}
