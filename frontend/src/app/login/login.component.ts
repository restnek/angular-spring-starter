import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {DisplayMessage} from '../shared/models/display-message';
import {AuthService, UserService} from '../service';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {CustomValidators} from '../shared/validation';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  returnUrl: string;
  hidePassword = true;

  /**
   * Boolean used in telling the UI
   * that the form has been submitted
   * and is awaiting a response
   */
  // submitted = false;

  /**
   * Notification message from received
   * form request or router
   */
  // notification: DisplayMessage;

  // private ngUnsubscribe: Subject<void> = new Subject<void>();

  constructor(
    // private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {
    this.initLoginForm();
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  initLoginForm() {
    this.loginForm = this.formBuilder.group({
      username: ['', [CustomValidators.notBlank, Validators.minLength(4), Validators.maxLength(20)]],
      password: ['', [CustomValidators.notBlank, Validators.maxLength(100)]],
    });
  }

  get controls() {
    return this.loginForm.controls;
  }

  togglePasswordVisibility(event: MouseEvent) {
    event.stopPropagation();
    this.hidePassword = !this.hidePassword;
  }

  // ngOnInit() {
  //   this.route.params
  //     .pipe(takeUntil(this.ngUnsubscribe))
  //     .subscribe((params: DisplayMessage) => {
  //       this.notification = params;
  //     });
  // }

  // ngOnDestroy() {
  //   this.ngUnsubscribe.next();
  //   this.ngUnsubscribe.complete();
  // }

  // onResetCredentials() {
  //   this.userService.resetCredentials()
  //     .pipe(takeUntil(this.ngUnsubscribe))
  //     .subscribe(res => {
  //       if (res.result === 'success') {
  //         alert('Password has been reset to 123 for all accounts');
  //       } else {
  //         alert('Server error');
  //       }
  //     });
  // }

  onSubmit() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value)
        .subscribe(res => {
          console.log(res);
          this.router.navigateByUrl(this.returnUrl);
        }, err => {
          console.log(err);
        });
    }

  //   this.notification = undefined;
  //   this.submitted = true;
  //
  //   this.authService.login(this.form.value)
  //     .subscribe(data => {
  //       this.userService.getMyInfo().subscribe();
  //       this.router.navigate([this.returnUrl]);
  //     }, error => {
  //       this.submitted = false;
  //       this.notification = {msgType: 'error', msgBody: 'Incorrect username or password.'};
  //     });
  }
}
