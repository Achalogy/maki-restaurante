import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { ShoppingCartService } from "src/app/service/ui/shopping-card.service";
import { AuthService } from "src/app/service/data/auth.service";

@Component({
  selector: "app-header-menu",
  templateUrl: "./header-menu.component.html",
  styleUrls: ["./header-menu.component.css"],
})
export class HeaderMenuComponent implements OnInit {
  public loggedRole: string | null = null;
  public loggedId: string | null = null;

  constructor(
    private router: Router,
    public cart: ShoppingCartService,
    public authService: AuthService,
  ) {}

  ngOnInit() {
    this.loggedRole = this.authService.getRole();
    this.loggedId = this.authService.getUserId();
  }

  goToProfile() {
    if (this.loggedRole === "CLIENT") this.router.navigate([`/client`]);
    else if (this.loggedRole === "OPERATOR") {
      this.router.navigate(["/operator/gateway"]);
    } else if (this.loggedRole === "ADMIN") {
      this.router.navigate(["/admin"]);
    }
  }

  navigateTo(url: string) {
    this.router.navigate([url]);
  }
}
