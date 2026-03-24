import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PLATES } from 'src/app/data/platos.data';
import { Plate } from 'src/app/interfaces/plate.interface';

@Component({
  selector: 'app-plate-view',
  templateUrl: './plate-view.component.html',
  styleUrls: ['./plate-view.component.css']
})
export class PlateViewComponent {
  plate: Plate | undefined;
  specialInstructions: string = '';

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    
    this.plate = PLATES.find(p => p.id === id);
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
