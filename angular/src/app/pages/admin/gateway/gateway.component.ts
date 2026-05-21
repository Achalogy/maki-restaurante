import { Component } from "@angular/core";
import { Router } from "@angular/router";

export interface AdminGatewayButtonData {
  title: string;
  description: string;
  icon: string;
  href: string;
}

@Component({
  selector: "app-gateway",
  templateUrl: "./gateway.component.html",
  styleUrls: ["./gateway.component.css"],
})
export class GatewayComponent {
  public buttons: AdminGatewayButtonData[] = [
    {
      title: "Operadores",
      description: "Gestionar operadores",
      icon: "ti ti-users-group",
      href: "/operator/crud",
    },
    {
      title: "Clientes",
      description: "Gestionar clientes",
      icon: "ti ti-users",
      href: "/client/crud",
    },
    {
      title: "Platos",
      description: "Gestionar platos",
      icon: "ti ti-tools-kitchen",
      href: "/plate/crud",
    },
    {
      title: "Adicionales",
      description: "Gestionar adicionales",
      icon: "ti ti-paper-bag",
      href: "/additional/crud",
    },
    {
      title: "Pedidos",
      description: "Gestionar pedidos",
      icon: "ti ti-send",
      href: "/purchase-order/adminview",
    },
    {
      title: "Domiciliarios",
      description: "Gestionar domiciliarios",
      icon: "ti ti-truck-delivery",
      href: "/delivery/crud",
    },
    {
      title: "Log In",
      description: "Iniciar sesión con otra cuenta",
      icon: "ti ti-truck-delivery",
      href: "/log-in",
    },
    {
      title: "Cerrar sesión",
      description: "Cerrar sesión y volver al inicio",
      icon: "ti ti-logout",
      href: "/log-out",
    },
  ];

  public constructor(private router: Router) {}

  navigateTo(url: string) {
    this.router.navigate([url]);
  }
}
