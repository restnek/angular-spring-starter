import { AbstractControl, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { allMatch, anyMatch } from './matchers';

const SPECIALS = ' !”#$%&’()*+,-./:;<=>?@[\\]^_`{|}~';
const DIGITS = '1234567890';
const LATIN_UPPER_LETTERS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
const LATIN_LOWER_LETTERS = 'abcdefghijklmnopqrstuvwxyz';
const LATIN_LETTERS = LATIN_UPPER_LETTERS + LATIN_LOWER_LETTERS;
const LATIN_ALPHANUMERIC = LATIN_LETTERS + DIGITS;

export function usernameValidators(): ValidatorFn[] {
  return [
    CustomValidators.latinAlphanumeric,
    CustomValidators.size(4, 20)
  ];
}

export function passwordValidators(minLength: number, maxLength: number): ValidatorFn[] {
  return [
    CustomValidators.anyUpper,
    CustomValidators.anyLower,
    CustomValidators.anyDigit,
    CustomValidators.anySpecial,
    CustomValidators.size(minLength, maxLength)
  ];
}

export function nameValidators(length: number): ValidatorFn[] {
  return [
    Validators.required,
    CustomValidators.latinLetters,
    Validators.maxLength(length)
  ];
}

export class CustomValidators {
  static size(min: number, max: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const length = control.value.length;
      if (length >= min && length <= max) {
        return null;
      }
      return {
        size: {
          actual: length,
          threshold: length > max ? max : min
        }
      };
    };
  }

  static latinAlphanumeric(control: AbstractControl): ValidationErrors | null {
    const isLatinAlphanumeric = allMatch(control.value, LATIN_ALPHANUMERIC);
    if (isLatinAlphanumeric) {
      return null;
    }
    return {
      latinAlphanumeric: isLatinAlphanumeric
    };
  }

  static anyUpper(control: AbstractControl): ValidationErrors | null {
    const isAnyUpper = anyMatch(control.value, LATIN_UPPER_LETTERS);
    if (isAnyUpper) {
      return null;
    }
    return {
      anyUpper: isAnyUpper
    };
  }

  static anyLower(control: AbstractControl): ValidationErrors | null {
    const isAnyLower = anyMatch(control.value, LATIN_LOWER_LETTERS);
    if (isAnyLower) {
      return null;
    }
    return {
      anyLower: isAnyLower
    };
  }

  static anyDigit(control: AbstractControl): ValidationErrors | null {
    const isAnyDigit = anyMatch(control.value, DIGITS);
    if (isAnyDigit) {
      return null;
    }
    return {
      anyDigit: isAnyDigit
    };
  }

  static anySpecial(control: AbstractControl): ValidationErrors | null {
    const isAnySpecial = anyMatch(control.value, SPECIALS);
    if (isAnySpecial) {
      return null;
    }
    return {
      anySpecial: isAnySpecial
    };
  }

  static latinLetters(control: AbstractControl): ValidationErrors | null {
    const isLatinLetters = allMatch(control.value, LATIN_LETTERS);
    if (isLatinLetters) {
      return null;
    }
    return {
      latinLetters: isLatinLetters
    };
  }
}
