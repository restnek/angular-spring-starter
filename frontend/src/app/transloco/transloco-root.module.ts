import { HttpClient } from '@angular/common/http';
import { Injectable, NgModule } from '@angular/core';
import {
  Translation,
  TRANSLOCO_CONFIG,
  TRANSLOCO_LOADER,
  translocoConfig,
  TranslocoLoader,
  TranslocoModule
} from '@ngneat/transloco';
import {
  TRANSLOCO_PERSIST_LANG_STORAGE,
  TranslocoPersistLangModule
} from '@ngneat/transloco-persist-lang';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TranslocoHttpLoader implements TranslocoLoader {
  constructor(private http: HttpClient) {
  }

  getTranslation(lang: string): Observable<Translation> {
    return this.http.get<Translation>(`/assets/i18n/${lang}.json`);
  }
}

@NgModule({
  imports: [
    TranslocoPersistLangModule.init({
      storage: {
        provide: TRANSLOCO_PERSIST_LANG_STORAGE,
        useValue: localStorage
      }
    })
  ],
  exports: [TranslocoModule],
  providers: [
    {
      provide: TRANSLOCO_CONFIG,
      useValue: translocoConfig({
        availableLangs: ['en', 'ru'],
        defaultLang: 'en',
        fallbackLang: 'en',
        reRenderOnLangChange: true,
        prodMode: environment.production
      })
    },
    {
      provide: TRANSLOCO_LOADER,
      useClass: TranslocoHttpLoader
    }
  ]
})
export class TranslocoRootModule {
}
