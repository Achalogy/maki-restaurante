import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Aditional } from 'src/app/interfaces/aditional.interface';
import { Category } from 'src/app/interfaces/category.interface';
import { AditionalService } from 'src/app/service/aditional.service';
import { CategoryService } from 'src/app/service/category.service';

@Component({
  selector: 'app-aditional-edit',
  templateUrl: './aditional-edit.component.html',
  styleUrls: ['./aditional-edit.component.css']
})
export class AditionalEditComponent implements OnInit {
  aditional: Aditional = {
    name: "",
    price: 0,
    id: -1,
  }
  categoryList: Category[] = []
  selectedCategoryList: Category[] = []

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private categoryService: CategoryService,
    private aditionalService: AditionalService
  ) { }

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.aditionalService.selectById(id).subscribe(ad => {
      this.aditional = ad;
      this.updateLists();
    });
  }

  updateLists() {
    this.categoryService.selectAll().subscribe((cats) => {
      this.categoryList = cats;
    });
    this.aditionalService.selectAllCategoriesByAditionalId(this.aditional.id).subscribe((cats) => {
      this.selectedCategoryList = cats.map(c => c.category);
    });
  }

  // 3. Función clave para que Angular sepa qué opciones del <select multiple> marcar
  compareCategory(c1: Category, c2: Category): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  updateAditional() {
    this.aditionalService.update(this.aditional.id, this.aditional).subscribe(() => {
      this.aditionalService.setCategories(this.aditional.id, this.selectedCategoryList).subscribe(() => {
        // this.updateLists()
        this.router.navigate(["/aditional/crud"])
      })
    })
  }
}