import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AdministratorService } from 'src/app/service/data/admin.service';

// app/service/admin.service

@Component({
  selector: 'app-admin-log-in',
  templateUrl: './admin-log-in.component.html',
  styleUrls: ['./admin-log-in.component.css']
})
export class AdminLogInComponent {

  username: string = '';
  password: string = '';
  loginError: boolean = false;

  constructor(
    private administratorService: AdministratorService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.loginError = false;
    this.administratorService.logIn({
      username: this.username,
      password: this.password
    }).subscribe({
      next: (administrator) => {
        this.router.navigate([`/admin`]);
        
        window.localStorage
          .setItem("loggedAs", "admin")
        window.localStorage
          .setItem("id", administrator.id.toString())
      },
      error: (err) => {
        if (err.status === 400 || err.status === 401) {
          this.loginError = true;
        } else {
          console.error(err);
        }
      }
    });
  }

  navigateTo(url: string) {
    this.router.navigate([url])
  }
}

