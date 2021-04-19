import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {DisplayMessage} from '../shared/models/display-message';
import {AuthService, UserService} from '../service';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {CustomValidators, notBlankValidator} from '../shared/validation';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  signUpForm: FormGroup;
  hidePassword = true;
  returnUrl: string;

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
  //   private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {
    this.initSignUpForm();
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  initSignUpForm() {
    this.signUpForm = this.formBuilder.group({
      username: ['', [CustomValidators.notBlank, Validators.minLength(4), Validators.maxLength(20)]],
      password: ['', [CustomValidators.notBlank, Validators.maxLength(100)]],
      firstname: ['', [CustomValidators.notBlank, Validators.maxLength(50)]],
      lastname: ['', [CustomValidators.notBlank, Validators.maxLength(50)]]
    })
  }

  togglePasswordVisibility(event: MouseEvent) {
    event.stopPropagation();
    this.hidePassword = !this.hidePassword;
  }

  get controls() {
    return this.signUpForm.controls;
  }

  ngOnInit() {
  //   this.route.params
  //     .pipe(takeUntil(this.ngUnsubscribe))
  //     .subscribe((params: DisplayMessage) => {
  //       this.notification = params;
  //     });
  }

  // ngOnDestroy() {
  //   this.ngUnsubscribe.next();
  //   this.ngUnsubscribe.complete();
  // }

  onSubmit() {
    if (this.signUpForm.valid) {
      this.authService.signup(this.signUpForm.value)
        .subscribe(res => {
          this.router.navigateByUrl(this.returnUrl);
        }, err => {
          console.log(err);
        });
    }

  //   this.notification = undefined;
  //   this.submitted = true;
  //
  //   this.authService.signup(this.form.value)
  //     .subscribe(data => {
  //       console.log(data);
  //       this.authService.login(this.form.value).subscribe(() => {
  //         this.userService.getMyInfo().subscribe();
  //       });
  //       this.router.navigate([this.returnUrl]);
  //     }, error => {
  //       this.submitted = false;
  //       console.log('Sign up error' + JSON.stringify(error));
  //       this.notification = {msgType: 'error', msgBody: error.error.errorMessage};
  //     });
  }
}
