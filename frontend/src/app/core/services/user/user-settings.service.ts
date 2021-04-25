import { Injectable } from '@angular/core';
import { TranslocoService } from '@ngneat/transloco';

@Injectable({
  providedIn: 'root'
})
export class UserSettingsService {
  constructor(private translocoService: TranslocoService) {
  }

  changeLanguage(language: string): void {
    this.translocoService.setActiveLang(language);
  }
}
