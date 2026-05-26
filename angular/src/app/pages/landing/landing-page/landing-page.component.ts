import { Component } from "@angular/core";
import { AuthService } from "src/app/service/data/auth.service";

@Component({
  selector: "app-landing-page",
  templateUrl: "./landing-page.component.html",
  styleUrls: ["./landing-page.component.css"],
})
export class LandingPageComponent {
  constructor(private authService: AuthService) {}

  // ngOnInit(): void {
  //   if (this.authService.isLoggedIn()) {
  //     this.authService.redirectBasedOnRole();
  //   }
  // }
}
