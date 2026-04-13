import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Client } from 'src/app/interfaces/client.interface';

@Component({
  selector: 'app-client-sign-up',
  templateUrl: './client-sign-up.component.html',
  styleUrls: ['./client-sign-up.component.css']
})
export class ClientSignUpComponent {

  client: Partial<Client> = {};

  constructor(
      private router: Router
    ) { }
  
    navigateTo(url: string) {
      this.router.navigate([url])
    }

}
