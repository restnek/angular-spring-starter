import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../core/services/api/auth.service';
import { ErrorService } from '../core/services/util/error.service';
import { passwordValidators } from '../core/util/validation';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent {
  changePasswordForm: FormGroup;
  hideOldPassword = true;
  hideNewPassword = true;

  constructor(
    private authService: AuthService,
    private router: Router,
    private formBuilder: FormBuilder,
    public errorService: ErrorService
  ) {
    this.initChangePasswordForm();
  }

  private initChangePasswordForm(): void {
    this.changePasswordForm = this.formBuilder.group({
      oldPassword: ['', passwordValidators(6, 100)],
      newPassword: ['', passwordValidators(6, 100)]
    });
  }

  toggleOldPasswordVisibility(event: MouseEvent): void {
    event.stopPropagation();
    this.hideOldPassword = !this.hideOldPassword;
  }

  toggleNewPasswordVisibility(event: MouseEvent): void {
    event.stopPropagation();
    this.hideNewPassword = !this.hideNewPassword;
  }

  onSubmit(): void {
    if (this.changePasswordForm.valid) {
      this.authService.changePassword(this.changePasswordForm.value)
        .subscribe(() => {
          this.router.navigate(['/']);
        }, err => {
          this.errorService.renderServerErrors(this.changePasswordForm, err);
        });
    }
  }
}
