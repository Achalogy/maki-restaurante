import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "src/app/service/data/auth.service";

@Component({
  selector: "app-log-in",
  templateUrl: "./log-in.component.html",
  styleUrls: ["./log-in.component.css"],
})
export class LogInComponent {
  username = "";
  password = "";
  loginError = false;

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  onSubmit(): void {
    this.loginError = false;
    this.authService
      .login({
        username: this.username,
        password: this.password,
      })
      .subscribe({
        next: () => {
          this.authService.redirectBasedOnRole();
        },
        error: () => {
          this.loginError = true;
        },
      });
  }

  navigateTo(url: string) {
    this.router.navigate([url]);
  }
}
