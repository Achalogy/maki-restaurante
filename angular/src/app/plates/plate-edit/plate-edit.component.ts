import { Component } from '@angular/core';
import { Plate } from 'src/app/interfaces/plate.interface';

@Component({
  selector: 'app-plate-edit',
  templateUrl: './plate-edit.component.html',
  styleUrls: ['./plate-edit.component.css']
})
export class PlateEditComponent {
  plate: Plate = {
    id: 0,
    name: '',
    description: '',
    price: 0,
    urlImage: '',
    category: { id: 0, name: "" },
    available: true
  };
  updatePlate() {
    console.log('Actualizando plato:', this.plate);
  }
}
