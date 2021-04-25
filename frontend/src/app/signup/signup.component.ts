import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../core/services/api/auth.service';
import { ErrorService } from '../core/services/util/error.service';
import { nameValidators, passwordValidators, usernameValidators } from '../core/util/validation';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  signUpForm: FormGroup;
  hidePassword = true;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    public errorService: ErrorService,
  ) {
    this.initSignUpForm();
  }

  private initSignUpForm(): void {
    this.signUpForm = this.formBuilder.group({
      username: ['', usernameValidators()],
      password: ['', passwordValidators(6, 100)],
      firstname: ['', nameValidators(50)],
      lastname: ['', nameValidators(50)]
    });
  }

  togglePasswordVisibility(event: MouseEvent): void {
    event.stopPropagation();
    this.hidePassword = !this.hidePassword;
  }

  onSubmit(): void {
    if (this.signUpForm.valid) {
      this.authService.signup(this.signUpForm.value)
        .subscribe(() => {
          this.router.navigate(['/']);
        }, err => {
          this.errorService.renderServerErrors(this.signUpForm, err);
        });
    }
  }
}
