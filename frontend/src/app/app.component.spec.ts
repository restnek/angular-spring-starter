import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TestBed, waitForAsync } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconRegistry } from '@angular/material/icon';
import { RouterTestingModule } from '@angular/router/testing';
import { AngularMaterialModule } from './angular-material/angular-material.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ApiCardComponent } from './api-card/api-card.component';
import { FooterComponent } from './core/components/footer/footer.component';
import { AccountMenuComponent } from './core/components/header/account-menu/account-menu.component';
import { HeaderComponent } from './core/components/header/header.component';
import { ApiService } from './core/services/api/api.service';
import { AuthService } from './core/services/api/auth.service';
import { FooService } from './core/services/api/foo.service';
import { UserService } from './core/services/api/user.service';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { SignupComponent } from './signup/signup.component';

describe('AppComponent', () => {
  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        HeaderComponent,
        FooterComponent,
        ApiCardComponent,
        HomeComponent,
        LoginComponent,
        NotFoundComponent,
        AccountMenuComponent,
        ChangePasswordComponent,
        SignupComponent
      ],
      imports: [
        AngularMaterialModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        RouterTestingModule,
        AppRoutingModule
      ],
      providers: [
        MatIconRegistry,
        {
          provide: ApiService
        },
        AuthService,
        UserService,
        FooService
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();
  }));

  it('should create the app', waitForAsync(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
