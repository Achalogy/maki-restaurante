import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AdminGatewayButtonData } from '../../gateway/gateway.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-gateway-button',
  templateUrl: './admin-gateway-button.component.html',
  styleUrls: ['./admin-gateway-button.component.css']
})
export class AdminGatewayButtonComponent {
  @Input() data: AdminGatewayButtonData | null = null

  public constructor(
    private router: Router
  ) {

  }

  navigate() {
    this.router.navigate([this.data?.href])
  }
}
