import { Component, OnDestroy } from '@angular/core';
import { TranslocoService } from '@ngneat/transloco';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnDestroy {
  private subscriptions: Subscription;

  title: string;
  message: string;

  constructor(private translocoService: TranslocoService) {
  }

  ngOnDestroy(): void {
    this.unsubscribe();
  }

  setSubscription(titleKey: string, messageKey: string): void {
    this.unsubscribe();

    const titleSubscription = this.translocoService.selectTranslate(titleKey)
      .subscribe(value => this.title = value);
    const messageSubscription = this.translocoService.selectTranslate(messageKey)
      .subscribe(value => this.message = value);

    this.subscriptions = new Subscription();
    this.subscriptions.add(titleSubscription);
    this.subscriptions.add(messageSubscription);
  }

  private unsubscribe(): void {
    this.subscriptions?.unsubscribe();
  }
}
