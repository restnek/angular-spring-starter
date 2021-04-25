import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { AngularMaterialModule } from '../angular-material/angular-material.module';
import { TranslocoRootModule } from '../transloco/transloco-root.module';
import { FooterComponent } from './components/footer/footer.component';
import { AccountMenuComponent } from './components/header/account-menu/account-menu.component';
import { HeaderComponent } from './components/header/header.component';
import { NotificationComponent } from './components/notification/notification.component';
import { ApiService } from './services/api/api.service';
import { AuthService } from './services/api/auth.service';
import { FooService } from './services/api/foo.service';
import { UserService } from './services/api/user.service';
import { SessionService } from './services/user/session.service';
import { UserSettingsService } from './services/user/user-settings.service';
import { ErrorService } from './services/util/error.service';
import { NotificationService } from './services/util/notification.service';

@NgModule({
  declarations: [
    AccountMenuComponent,
    HeaderComponent,
    FooterComponent,
    NotificationComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule,
    TranslocoRootModule,
    AngularMaterialModule,
    BrowserAnimationsModule,
    FlexLayoutModule
  ],
  exports: [
    RouterModule,
    TranslocoRootModule,
    AngularMaterialModule,
    FlexLayoutModule,
    HeaderComponent,
    FooterComponent
  ],
  providers: [
    ApiService,
    AuthService,
    ErrorService,
    FooService,
    NotificationService,
    SessionService,
    UserService,
    UserSettingsService
  ]
})
export class CoreModule {
}
