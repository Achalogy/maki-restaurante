import { Component } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Client } from "src/app/interfaces/client.interface";
import { AuthService } from "src/app/service/data/auth.service";
import { ClientService } from "src/app/service/data/client.service";

@Component({
  selector: "app-client-session",
  templateUrl: "./client-session.component.html",
  styleUrls: ["./client-session.component.css"],
})
export class ClientSessionComponent {
  client: Client | undefined;

  constructor(
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
  ) {}
  ngOnInit(): void {
    const id = +this.authService.getUserId()!;

    this.clientService.selectById(id).subscribe((client) => {
      if (!client) {
        this.router.navigate(["/client/crud"]);
      } else {
        this.client = client;
      }
    });
  }

  logOut(): void {
    this.router.navigate(["/log-out"]);
  }
}
