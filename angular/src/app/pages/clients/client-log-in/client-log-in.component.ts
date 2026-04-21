import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Client } from 'src/app/interfaces/client.interface';
import { ClientService } from 'src/app/service/data/client.service';

@Component({
  selector: 'app-client-log-in',
  templateUrl: './client-log-in.component.html',
  styleUrls: ['./client-log-in.component.css']
})
export class ClientLogInComponent {

  email: string = "";
  password: string = ""

  constructor(
    private clientService: ClientService,
    private router: Router
  ) { }

  navigateTo(url: string) {
    this.router.navigate([url])
  }

  onSubmit(): void {
    this.clientService.logIn({
      email: this.email,
      password: this.password
    } as Partial<Client>).subscribe({
      next: (valid) => {

        window.localStorage
          .setItem("loggedAs", "client")
        window.localStorage
          .setItem("id", valid.id.toString())

        this.router.navigate([`/client/${valid.id}`]);
      },
      error: (err) => {
        if (err.status === 400) {
          alert("credenciales incorrectas")
          this.router.navigate(["/client/log-in"], {
            queryParams: { error: 'credentials' }
          });
         } else {
          console.error(err);
        }
      }
    });
  }
}
