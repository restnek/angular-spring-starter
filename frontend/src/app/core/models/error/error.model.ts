import { ValidationError } from './validation-error.model';

export interface Error {
  type: string;
  message: string;
  errors: ValidationError[] | null;
}
