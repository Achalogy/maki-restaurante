import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Client } from 'src/app/interfaces/client.interface';
import { ClientService } from 'src/app/service/data/client.service';

@Component({
  selector: 'app-client-crud',
  templateUrl: './client-crud.component.html',
  styleUrls: ['./client-crud.component.css']
})
export class ClientCrudComponent {

  clientes: Client[] = [];

  constructor(
    private clientService: ClientService,
        private router: Router
  ) {

  }
  ngOnInit() {
    this.clientService.selectAll().subscribe(clients => this.clientes = clients) 
  }

  deleteClient(id: number){
    if (confirm('¿Estás seguro de que deseas eliminar este cliente?')) {
      this.clientService.delete(id).subscribe(() => {
        this.clientService.selectAll().subscribe(clients => this.clientes = clients) 
      })
    }

  }

  navigateTo(url: string) {
    this.router.navigate([url])
  }

}
