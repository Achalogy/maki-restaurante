import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ShoppingCartService } from 'src/app/service/ui/shopping-card.service';

@Component({
  selector: 'app-header-menu',
  templateUrl: './header-menu.component.html',
  styleUrls: ['./header-menu.component.css']
})
export class HeaderMenuComponent {
  public loggedAs: "client" | "operator" | null = null;
  public loggedId: string |  null = null;

  constructor(
    private router: Router,
    public cart: ShoppingCartService
  ) {}

  ngOnInit() {
    this.loggedAs = window.localStorage.getItem("loggedAs") as any
    this.loggedId = window.localStorage.getItem("id") as any
  }

  goToProfile() {
    if(this.loggedAs == "client")
      this.router.navigate([`/client/${this.loggedId}`])
    else if(this.loggedAs == "operator") {
      this.router.navigate(["/operator/gateway"])
    } else {
      this.router.navigate(["/admin"])
    }
  }

  navigateTo(url: string) {
    this.router.navigate([url])
  }
}
