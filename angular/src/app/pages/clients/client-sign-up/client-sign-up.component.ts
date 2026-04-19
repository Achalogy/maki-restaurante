import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Client } from 'src/app/interfaces/client.interface';
import { ClientService } from 'src/app/service/client.service';

@Component({
  selector: 'app-client-sign-up',
  templateUrl: './client-sign-up.component.html',
  styleUrls: ['./client-sign-up.component.css']
})
export class ClientSignUpComponent {

  client: Partial<Client> = {};

  constructor(
    private router: Router,
    private clientService: ClientService
  ) { }

  navigateTo(url: string) {
    this.router.navigate([url]);
  }

  onSubmit() {
    this.clientService.create(this.client as Omit<Client, 'id'>).subscribe({
      next: (client) => {
          this.router.navigate(['/client/' + client.id]);

          window.localStorage
            .setItem("loggedAs", "client")
          window.localStorage
            .setItem("id", client.id.toString())
      },
      error: (err) => {
        if (err.status === 400) {
          alert("Correo ya registrado")
          this.router.navigate(["/client/sign-up"], {
            queryParams: { error: 'credentials' }
          });
        } else {
          console.error(err);
        }
      }
    })



  }
}