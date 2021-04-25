import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-webp-image',
  templateUrl: './webp-image.component.html',
  styleUrls: ['./webp-image.component.scss']
})
export class WebpImageComponent {
  @Input() webpUrl: string;
  @Input() fallbackImgUrl: string;
  @Input() alt: string;
}
