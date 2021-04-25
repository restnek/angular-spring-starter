import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AngularMaterialModule } from '../angular-material/angular-material.module';
import { WebpImageComponent } from './webp-image/webp-image.component';
import { InputFieldComponent } from './input-field/input-field.component';
import { FieldErrorComponent } from './field-error/field-error.component';

@NgModule({
  declarations: [
    WebpImageComponent,
    InputFieldComponent,
    FieldErrorComponent
  ],
  imports: [
    CommonModule,
    AngularMaterialModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    WebpImageComponent,
    InputFieldComponent,
    FieldErrorComponent
  ]
})
export class SharedModule {
}
