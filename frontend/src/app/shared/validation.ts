import {AbstractControl, ValidatorFn} from '@angular/forms';

export function notBlankValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const value = control.value || '';
    if (value.trim().length > 0) {
      return null;
    }
    return {
      notblank: {
        whitespaces: value.length,
        empty: !value.length
      }
    }
  };
}

export class CustomValidators {
  static notBlank(control: AbstractControl): any | null {
    return notBlankValidator()(control);
  }
}
