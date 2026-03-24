import { Component } from '@angular/core';
import { Plate } from 'src/app/interfaces/plate.interface';

@Component({
  selector: 'app-plate-create',
  templateUrl: './plate-create.component.html',
  styleUrls: ['./plate-create.component.css']
})
export class PlateCreateComponent {
  plate: Plate = {
    id: 0,
    name: '',
    description: '',
    price: 0,
    urlImage: '',
    category: { id: 0, name: "" },
    available: true
  };

  savePlate() {
    this.plate.id = Math.floor(Math.random() * 1000);
    console.log('Plato guardado:', this.plate);

    this.resetForm();
  }

  resetForm() {
    this.plate = {
      id: 0,
      name: '',
      description: '',
      price: 0,
      urlImage: '',
      category: { id: 0, name: "" },
      available: true
    };
  }
}
