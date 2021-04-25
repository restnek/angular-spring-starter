export abstract class ApiPaths {
  static readonly PREFIX = '/api';
  static readonly AUTH = ApiPaths.PREFIX + '/auth';

  static readonly FOO = ApiPaths.PREFIX + '/foo';

  static readonly USERS = ApiPaths.PREFIX + '/users';

  static readonly LOGIN = ApiPaths.AUTH + '/login';
  static readonly LOGOUT = ApiPaths.AUTH + '/logout';
  static readonly SIGN_UP = ApiPaths.AUTH + '/signup';
  static readonly WHO_AM_I = ApiPaths.AUTH + '/whoami';
  static readonly REFRESH = ApiPaths.AUTH + '/refresh';
  static readonly CHANGE_PASSWORD = ApiPaths.AUTH + '/change-password';

  static resolve(...path: (string | number)[]): string {
    return path.join('/');
  }
}
