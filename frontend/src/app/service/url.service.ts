import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UrlService {
  private prefix = '/api';
  readonly foo = this.prefix + '/foo';
  readonly users = this.prefix + '/users';
  readonly auth = {
    login: this.prefix + '/auth/login',
    logout: this.prefix + '/auth/logout',
    signUp: this.prefix + '/auth/signup',
    whoAmI: this.prefix + '/auth/whoami',
    refresh: this.prefix + '/auth/refresh',
    changePassword: this.prefix + '/auth/change-password'
  }

  resolve(...path: (string | number)[]) {
    return path.join('/');
  }
}
