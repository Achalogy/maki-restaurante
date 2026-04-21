import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-gateway',
  templateUrl: './gateway.component.html',
  styleUrls: ['./gateway.component.css']
})
export class GatewayComponent {

  public constructor(
    private router: Router
  ) {

  }

  navigateTo(url: string) {
    this.router.navigate([url])
  }
}
