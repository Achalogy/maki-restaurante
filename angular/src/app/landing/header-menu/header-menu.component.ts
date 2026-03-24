import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header-menu',
  templateUrl: './header-menu.component.html',
  styleUrls: ['./header-menu.component.css']
})
export class HeaderMenuComponent {

  constructor(
    private router: Router
  ) {}

  navigateTo(url: string) {
    this.router.navigate([url])
  }
}
