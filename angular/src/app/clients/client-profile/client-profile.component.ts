import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Client } from 'src/app/interfaces/client.interface';
import { ClientService } from 'src/app/service/client.service';

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
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    const found = this.clientService.selectById(id);

    if (found) {
      this.client = { ...found };
    } else {
      this.router.navigate(['/client/crud']);
    }
  }

  onUpdate(): void {
    if (this.client) {
      this.clientService.update(this.client.id, this.client);
      this.router.navigate(['/client/session', this.client.id]);
    }
  }

  onDelete(): void {
    const confirmed = confirm('¿Estás seguro de eliminar tu cuenta? Esta acción no se puede deshacer.');
    if (this.client && confirmed) {
      this.clientService.delete(this.client.id);
      this.router.navigate(['/client/crud']);
    }
  }
}