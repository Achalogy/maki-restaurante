import { Injectable } from '@angular/core';
import { Client } from '../interfaces/client.interface';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor() { }

  clientList: Client[] = [
    { id: 1, name: 'Juan', surname: 'Pérez', email: 'juan.perez@example.com', password: 'pass123', phone: '123456789', address: 'Calle 1' },
    { id: 2, name: 'María', surname: 'García', email: 'maria.garcia@example.com', password: 'pass456', phone: '987654321', address: 'Calle 2' },
    { id: 3, name: 'Carlos', surname: 'López', email: 'carlos.lopez@example.com', password: 'pass789', phone: '555666777', address: 'Calle 3' },
  ];

  selectAll(): Client[] {
    return this.clientList;
  }

  selectById(id: number): Client | undefined {
    return this.clientList.find(client => client.id === id);
  }

  create(client: Omit<Client, 'id'>): Client {
    const newId = this.clientList.length > 0 ? Math.max(...this.clientList.map(c => c.id)) + 1 : 1;
    const newClient: Client = { id: newId, ...client };
    this.clientList.push(newClient);
    return newClient;
  }

  update(id: number, data: Partial<Client>): Client | null {
    const index = this.clientList.findIndex(client => client.id === id);
    if (index === -1) return null;

    this.clientList[index] = { ...this.clientList[index], ...data };
    return this.clientList[index];
  }

  delete(id: number): boolean {
    const index = this.clientList.findIndex(client => client.id === id);
    if (index === -1) return false;

    this.clientList.splice(index, 1);
    return true;
  }
}
