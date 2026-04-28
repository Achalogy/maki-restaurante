import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-log-out',
  templateUrl: './log-out.component.html',
  styleUrls: ['./log-out.component.css']
})
export class LogOutComponent {

  constructor(private router: Router) { }

  ngOnInit() {

    window.localStorage
      .removeItem("loggedAs")
    window.localStorage
      .removeItem("id")

    this.router.navigate(["/"])

  }
}
