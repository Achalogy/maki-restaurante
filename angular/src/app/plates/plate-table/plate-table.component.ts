import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Plate } from 'src/app/interfaces/plate.interface';
import { PlateService } from 'src/app/service/plate.service';

@Component({
  selector: 'app-plate-table',
  templateUrl: './plate-table.component.html',
  styleUrls: ['./plate-table.component.css']
})
export class PlateTableComponent {

  plateList: Plate[] = []

  constructor(
    private plateService: PlateService,
        private router: Router
  ) {

  }

  ngOnInit() {
    this.plateList = this.plateService.selectAll()
  }

  goToPlate(id: number) {
    this.router.navigate([`/plate/${id}`])
  }

  editPlate(id: number) {
    this.router.navigate([`/plate/${id}/edit`])
  }

  createPlate() {
    this.router.navigate([`/plate/create`])
  }

  deletePlateById(id: number) {
    if (confirm(
      '¿Estás seguro de que deseas eliminar este plato?',
    )) {
      this.plateService.delete(id)
      this.plateList = this.plateService.selectAll()
    }
  }
}
