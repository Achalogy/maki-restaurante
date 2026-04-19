import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'src/app/interfaces/category.interface';
import { Plate } from 'src/app/interfaces/plate.interface';
import { CategoryService } from 'src/app/service/category.service';
import { PlateService } from 'src/app/service/plate.service';

@Component({
  selector: 'app-plate-table',
  templateUrl: './plate-table.component.html',
  styleUrls: ['./plate-table.component.css']
})
export class PlateTableComponent {

  plateList: Plate[] = [];
  categoryList: Category[] = [];
  showModal: boolean = false; // Controla el Pop-up
  newCategoryName: string = ''; // Almacena el input

  constructor(
    private plateService: PlateService,
    private categoryService: CategoryService, // Inyecta el servicio
    private router: Router
  ) { }

  ngOnInit() {
    this.plateService.selectAll().subscribe(
      (plates) => this.plateList = plates
    )
    this.categoryService.selectAll().subscribe(categories => this.categoryList = categories)
  }

  goToPlate(id: number) {
    this.router.navigate([`/plate/${id}`])
  }

  toggleModal() {
    this.showModal = !this.showModal;
    this.newCategoryName = ''; // Limpiar al cerrar
  }

  saveCategory() {
    if (!this.newCategoryName.trim()) return;

    this.categoryService.create({ name: this.newCategoryName }).subscribe({
      next: () => {
        alert('Categoría guardada con éxito');
        this.toggleModal();
        this.categoryService.selectAll().subscribe(categories => this.categoryList = categories)
      },
      error: (err) => console.error('Error al crear categoría', err)
    });
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
      this.plateService.delete(id).subscribe(() => {
        this.plateService.selectAll().subscribe(
          (plates) => this.plateList = plates
        )
        this.categoryService.selectAll().subscribe(categories => this.categoryList = categories)
      })
      
    }
  }

  deleteCategory(id: number) {
    if (confirm(
      '¿Estás seguro de que deseas eliminar esta categoria?',
    )) {
      this.categoryService.delete(id).subscribe(() => {
        this.plateService.selectAll().subscribe(
          (plates) => this.plateList = plates
        )
        this.categoryService.selectAll().subscribe(categories => this.categoryList = categories)
      })
      
    }
  }
}
