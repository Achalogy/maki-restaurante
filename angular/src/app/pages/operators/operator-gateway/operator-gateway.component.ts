import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AdminGatewayButtonData } from '../../admin/gateway/gateway.component';

@Component({
  selector: 'app-operator-gateway',
  templateUrl: './operator-gateway.component.html',
  styleUrls: ['./operator-gateway.component.css']
})
export class OperatorGatewayComponent {

  public buttons: AdminGatewayButtonData[] = [
    {
      title: "Pedidos",
      description: "Gestionar pedidos",
      icon: "ti ti-send",
      href: "/purchase-order/adminview"
    },
    {
      title: "Cerrar sesión",
      description: "Cerrar sesión y volver al inicio",
      icon: "ti ti-logout",
      href: "/log-out"
    }
  ]

  public constructor(
    private router: Router
  ) {

  }

  navigateTo(url: string) {
    this.router.navigate([url])
  }
}
