import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Client } from 'src/app/interfaces/client.interface';
import { ClientService } from 'src/app/service/data/client.service';
import { OrderDetailsService } from 'src/app/service/data/order-details.service';

@Component({
  selector: 'app-client-profile',
  templateUrl: './client-profile.component.html',
  styleUrls: ['./client-profile.component.css']
})
export class ClientProfileComponent implements OnInit {
  client: Client | undefined;

  constructor(
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router,
    private orderDetailsService: OrderDetailsService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.clientService.selectById(id).subscribe(client => {
      if(!client)
        this.router.navigate(['/client/crud']);
      else 
      this.client = client
    })

  }

  onUpdate(): void {
    if (this.client) {
      this.clientService.update(this.client.id, this.client).subscribe((client) => {
        this.router.navigate([`/client/${client.id}`]);
      })
    }
  }

  onDelete(): void {
    const confirmed = confirm('¿Estás seguro de eliminar tu cuenta? Esta acción no se puede deshacer.');
    if (this.client && confirmed) {
      this.clientService.delete(this.client.id).subscribe(() => {
        this.router.navigate(['/client/crud']);
      })
    }
  }
}