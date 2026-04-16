import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Aditional } from 'src/app/interfaces/aditional.interface';
import { Plate } from 'src/app/interfaces/plate.interface';
import { AditionalService } from 'src/app/service/aditional.service';
import { PlateService } from 'src/app/service/plate.service';

@Component({
  selector: 'app-plate-view',
  templateUrl: './plate-view.component.html',
  styleUrls: ['./plate-view.component.css']
})
export class PlateViewComponent {
  plate?: Plate;
  specialInstructions: string = '';

  selectedAdditionals: number[] = [];

  additionalsList: Aditional[] = [];

  constructor(
    private route: ActivatedRoute,
    private plateService: PlateService,
    private aditionalService: AditionalService
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log(id)
    if (id) {
      this.plateService.selectById(id).subscribe((plate) => {
        this.plate = plate;
        // Aquí podrías cargar los adicionales basados en plate.category.id
        this.loadAdditionals(plate.category.id);
      });
    }
  }

  loadAdditionals(categoryId: number) {

    this.aditionalService.selectByCategoryId(categoryId).subscribe(aditionals => {
      this.additionalsList = aditionals
      console.log(this.additionalsList)
    })
    
  }

  onCheckboxChange(id: number, event: any) {
    if (event.target.checked) {
      this.selectedAdditionals.push(id);
    } else {
      this.selectedAdditionals = this.selectedAdditionals.filter(aid => aid !== id);
    }
  }

  addToOrder() {
    const orderItem = {
      plateId: this.plate?.id,
      instructions: this.specialInstructions,
      additionals: this.selectedAdditionals
    };
    console.log('Agregando al pedido:', orderItem);
    // Aquí llamarías a tu servicio de Carrito
    alert('Plato agregado al pedido');
  }
}
