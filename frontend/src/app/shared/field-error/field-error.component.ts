import { Component, Input, OnDestroy} from '@angular/core';
import { TranslocoService } from '@ngneat/transloco';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-field-error',
  templateUrl: './field-error.component.html',
  styleUrls: ['./field-error.component.scss']
})
export class FieldErrorComponent implements OnDestroy {
  private subscriptions: Subscription;
  private keyPrefix: string;
  private errorKeys: string[];
  messages: string[];

  constructor(private translocoService: TranslocoService) {
  }

  ngOnDestroy(): void {
    this.unsubscribe();
  }

  @Input()
  set translationPrefix(keyPrefix: string) {
    this.keyPrefix = keyPrefix;
    this.subscribe();
  }

  @Input()
  set errors(errorKeys: string[]) {
    this.errorKeys = errorKeys;
    this.subscribe();
  }

  private subscribe(): void {
    this.unsubscribe();

    if (Array.isArray(this.errorKeys)) {
      this.subscriptions = new Subscription();
      const messages = this.errorKeys.map(e => this.keyPrefix + '.' + e);

      messages.forEach((e, i, a) => {
        const subscription = this.translocoService.selectTranslate(e)
          .subscribe(v => a[i] = v);
        this.subscriptions.add(subscription);
      });

      this.messages = messages;
    }
  }

  private unsubscribe(): void {
    this.subscriptions?.unsubscribe();
  }
}
