import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { Category } from "src/app/interfaces/category.interface";
import { Plate } from "src/app/interfaces/plate.interface";
import { PlateService } from "src/app/service/data/plate.service";

@Component({
  selector: "app-plate-menu",
  templateUrl: "./plate-menu.component.html",
  styleUrls: ["./plate-menu.component.css"],
})
export class PlateMenuComponent {
  categorias: Category[] = [];
  plateList: Plate[] = [];

  constructor(
    private plateService: PlateService,
    private router: Router,
  ) {}
  ngOnInit() {
    this.plateService.selectAll().subscribe((plates) => {
      this.plateList = plates;
      console.log(this.plateList);
      this.categorias = [
        ...new Map(plates.map((p) => [p.category?.id, p.category])).values(),
      ];
    });
  }
  goToPlate(id: number) {
    this.router.navigate([`/plate/${id}`]);
  }
  getCategoryLabel(nombre: string): string {
    if (!nombre) return nombre;
    return nombre.includes("None") ? "No categorizado" : nombre;
  }
  platesForCategory(cat: string) {
    return this.plateList.filter((plate) => plate?.category?.name === cat);
  }
  navigateTo(url: string) {
    this.router.navigate([url]);
  }
}
