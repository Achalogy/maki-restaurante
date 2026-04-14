import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Plate } from 'src/app/interfaces/plate.interface';
import { PlateService } from 'src/app/service/plate.service';

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

  constructor(
    public plateService: PlateService,
    private router: Router
  ) {

  }

  savePlate() {
    this.plate.id = Math.floor(Math.random() * 1000);
    console.log('Plato guardado:', this.plate);

    this.plateService.create(this.plate).subscribe(() => {
      this.router.navigate(['/plate/crud']);
    })

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
