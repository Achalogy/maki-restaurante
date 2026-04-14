import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Client } from 'src/app/interfaces/client.interface';

@Component({
  selector: 'app-client-log-in',
  templateUrl: './client-log-in.component.html',
  styleUrls: ['./client-log-in.component.css']
})
export class ClientLogInComponent {

  email: string = "";
  password: string = ""

  constructor(
    private router: Router
  ) { }

  navigateTo(url: string) {
    this.router.navigate([url])
  }

  onSubmit(): void {
    
  }
}
