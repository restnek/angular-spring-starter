import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconRegistry } from '@angular/material/icon';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ApiCardComponent } from './api-card/api-card.component';
import { CoreModule } from './core/core.module';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { SharedModule } from './shared/shared.module';
import { SignupComponent } from './signup/signup.component';

@NgModule({
  declarations: [
    AppComponent,
    ApiCardComponent,
    HomeComponent,
    LoginComponent,
    NotFoundComponent,
    ChangePasswordComponent,
    SignupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    CoreModule,
    SharedModule
  ],
  providers: [
    MatIconRegistry
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
