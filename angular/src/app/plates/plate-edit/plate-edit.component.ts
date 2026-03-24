import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Plate } from 'src/app/interfaces/plate.interface';
import { PlateService } from 'src/app/service/plate.service';

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

  constructor(
    private plateService: PlateService,
    private route: ActivatedRoute,
    private router: Router
  ) {

  }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    const p = this.plateService.selectById(id)
    if (!p)
      this.router.navigate(['/plate/crud']);

    this.plate = p!
  }

  updatePlate() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log('Actualizando plato:', this.plate);
    this.plateService.update(id, this.plate)
    
    this.router.navigate(['/plate/crud']);
  }
}
