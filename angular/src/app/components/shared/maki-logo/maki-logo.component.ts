import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-maki-logo',
  templateUrl: './maki-logo.component.html',
  styleUrls: ['./maki-logo.component.css']
})
export class MakiLogoComponent {
  constructor(
    private router: Router
  ) {}

  navigateTo(url: string) {
    this.router.navigate([url])
  }
}
