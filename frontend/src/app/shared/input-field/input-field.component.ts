import { Component, Input } from '@angular/core';
import {
  ControlValueAccessor, NG_VALUE_ACCESSOR
} from '@angular/forms';

@Component({
  selector: 'app-input-field',
  templateUrl: './input-field.component.html',
  styleUrls: ['./input-field.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: InputFieldComponent
    }
  ]
})
export class InputFieldComponent implements ControlValueAccessor {
  @Input() label: string;
  @Input() secret: boolean;

  value: string;
  disabled = false;
  type: 'text' | 'password' = 'text';
  private touched = false;

  private externalOnChange = (value: string) => {};

  private externalOnTouched = () => {};

  writeValue(value: string): void {
    this.value = value;
  }

  registerOnChange(fn: any): void {
    this.externalOnChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.externalOnTouched = fn;
  }

  setDisabledState(disabled: boolean): void {
    this.disabled = disabled;
  }

  onChange(value: string): void {
    this.markAsTouched();
    this.externalOnChange(value);
  }

  private markAsTouched(): void {
    if (!this.touched) {
      this.externalOnTouched();
      this.touched = true;
    }
  }

  changeVisibility(event: MouseEvent): void {
    event.stopPropagation();
    this.type = (this.type === 'text') ? 'password' : 'text';
  }
}
