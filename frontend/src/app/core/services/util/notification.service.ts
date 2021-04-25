import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { NotificationComponent } from '../../components/notification/notification.component';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private config: MatSnackBarConfig = {
    duration: 10000,
    horizontalPosition: 'center',
    verticalPosition: 'top'
  };

  constructor(private snackBar: MatSnackBar) {
  }

  show(titleKey: string, messageKey: string): void {
    const snackBarRef = this.snackBar.openFromComponent(NotificationComponent, this.config);
    snackBarRef.instance.setSubscription(titleKey, messageKey);
  }
}
