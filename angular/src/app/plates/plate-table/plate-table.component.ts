import { Component } from '@angular/core';
import { PLATES } from 'src/app/data/platos.data';
import { Plate } from 'src/app/interfaces/plate.interface';

@Component({
  selector: 'app-plate-table',
  templateUrl: './plate-table.component.html',
  styleUrls: ['./plate-table.component.css']
})
export class PlateTableComponent {
  plateList: Plate[] = PLATES

  deletePlateById(id: number) {
    if (confirm(
      '¿Estás seguro de que deseas eliminar este plato?',
    )) {
      this.plateList =
        this.plateList.filter(p => p.id != id)
    }
  }
}
