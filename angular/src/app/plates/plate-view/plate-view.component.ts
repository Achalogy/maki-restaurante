import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Plate } from 'src/app/interfaces/plate.interface';
import { PlateService } from 'src/app/service/plate.service';

@Component({
  selector: 'app-plate-view',
  templateUrl: './plate-view.component.html',
  styleUrls: ['./plate-view.component.css']
})
export class PlateViewComponent {
  plate: Plate | undefined;
  specialInstructions: string = '';

  constructor(
    private route: ActivatedRoute,
    private plateService: PlateService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    
    this.plateService.selectById(id).subscribe(
      plate => this.plate = plate
    )
  }

  addToOrder() {
    console.log('Producto para el carrito:', {
      plateId: this.plate?.id,
      name: this.plate?.name,
      instructions: this.specialInstructions
    });
    alert('Añadido al pedido con éxito');
  }
}
