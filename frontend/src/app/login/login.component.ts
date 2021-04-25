import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../core/services/api/auth.service';
import { ErrorService } from '../core/services/util/error.service';
import { passwordValidators, usernameValidators } from '../core/util/validation';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  isBadCredentials = false;
  hidePassword = true;

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    public errorService: ErrorService
  ) {
    this.initLoginForm();
  }

  initLoginForm(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', usernameValidators()],
      password: ['', passwordValidators(6, 100)],
    });
  }

  togglePasswordVisibility(event: MouseEvent): void {
    event.stopPropagation();
    this.hidePassword = !this.hidePassword;
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value)
        .subscribe(res => {
          console.log(res);
          this.router.navigate(['/']);
        }, err => {
          this.isBadCredentials = err.error.type === 'badCredentials';
          this.errorService.renderServerErrors(this.loginForm, err);
        });
    }
  }
}
