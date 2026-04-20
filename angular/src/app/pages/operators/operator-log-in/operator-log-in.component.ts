import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { OperatorService } from 'src/app/service/data/operator.service';

// app/service/operator.service

@Component({
  selector: 'app-operator-log-in',
  templateUrl: './operator-log-in.component.html',
  styleUrls: ['./operator-log-in.component.css']
})
export class OperatorLogInComponent {

  username: string = '';
  password: string = '';

  constructor(
    private operatorService: OperatorService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.operatorService.logIn({
      username: this.username,
      password: this.password
    }).subscribe({
      next: (operator) => {
        this.router.navigate([`/operator/portal`]);
      },
      error: (err) => {
        if (err.status === 400 || err.status === 401) {
          alert('Credenciales incorrectas');
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
