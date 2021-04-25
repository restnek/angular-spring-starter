import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WebpImageComponent } from './webp-image.component';

describe('WebpImageComponent', () => {
  let component: WebpImageComponent;
  let fixture: ComponentFixture<WebpImageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WebpImageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WebpImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
