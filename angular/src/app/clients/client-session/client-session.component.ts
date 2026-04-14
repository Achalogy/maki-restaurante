import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Client } from 'src/app/interfaces/client.interface';
import { ClientService } from 'src/app/service/client.service';

@Component({
  selector: 'app-client-session',
  templateUrl: './client-session.component.html',
  styleUrls: ['./client-session.component.css']
})
export class ClientSessionComponent {
  client: Client | undefined;

  constructor(private clientService: ClientService,
      private route: ActivatedRoute,
      private router: Router){

  }
  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    
    this.clientService.selectById(id).subscribe(client => {
      if (!client) {
        this.router.navigate(['/client/crud']);
      } else {
        this.client = client;
      }

    })

  }

}
