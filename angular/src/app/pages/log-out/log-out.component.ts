import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "src/app/service/data/auth.service";

@Component({
  selector: "app-log-out",
  templateUrl: "./log-out.component.html",
  styleUrls: ["./log-out.component.css"],
})
export class LogOutComponent {
  constructor(
    private router: Router,
    private authService: AuthService,
  ) {}

  ngOnInit() {
    this.authService.logout();
  }
}
