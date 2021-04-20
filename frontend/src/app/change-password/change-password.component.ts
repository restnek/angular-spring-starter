import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {DisplayMessage} from '../shared/models/display-message';
import {AuthService} from '../service';
import {mergeMap} from 'rxjs/operators';
import {CustomValidators} from '../shared/validation';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent {
  changePasswordForm: FormGroup;

  /**
   * Boolean used in telling the UI
   * that the form has been submitted
   * and is awaiting a response
   */
  // submitted = false;

  /**
   * Diagnostic message from received
   * form request error
   */
  // notification: DisplayMessage;

  constructor(
    private authService: AuthService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.changePasswordForm = formBuilder.group({
      oldPassword: ['', [CustomValidators.notBlank, Validators.maxLength(100)]],
      newPassword: ['', [CustomValidators.notBlank, Validators.maxLength(100)]],
    });
  }

  get controls() {
    return this.changePasswordForm.controls;
  }

  onSubmit() {
    if (this.changePasswordForm.valid) {
      console.log('sen change password request');
    }

    // /**
    //  * Innocent until proven guilty
    //  */
    // this.notification = undefined;
    // this.submitted = true;

    // this.authService.changePassword(this.form.value)
    //   .pipe(mergeMap(() => this.authService.logout()))
    //   .subscribe(() => {
    //     this.router.navigate(['/login', {msgType: 'success', msgBody: 'Success! Please sign in with your new password.'}]);
    //   }, error => {
    //     this.submitted = false;
    //     this.notification = {msgType: 'error', msgBody: 'Invalid old password.'};
    //   });
  }
}
