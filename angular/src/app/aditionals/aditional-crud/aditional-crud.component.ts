import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AditionalCategory } from 'src/app/interfaces/aditional-category.interface';
import { Aditional } from 'src/app/interfaces/aditional.interface';
import { Category } from 'src/app/interfaces/category.interface';
import { AditionalService } from 'src/app/service/aditional.service';

@Component({
  selector: 'app-aditional-crud',
  templateUrl: './aditional-crud.component.html',
  styleUrls: ['./aditional-crud.component.css']
})
export class AditionalCrudComponent {

  aditionalList: Aditional[] = []
  aditionalCategories: AditionalCategory[] = []
  catsByAdId: Record<number, Category[]> = {}

  constructor(
    private router: Router,
    private aditionalService: AditionalService
  ) {}

  updateLists() {
    this.aditionalService.selectAll().subscribe(aditionals =>
        this.aditionalList = aditionals
    )
    
    this.aditionalService.selectAllCategories().subscribe(adcs => {
      this.aditionalCategories = adcs
      this.catsByAdId = {}

      for(const a of adcs) {
        if(!a.aditional) continue;
        this.catsByAdId[a.aditional.id] ??= []
        this.catsByAdId[a.aditional.id].push(a.category);
      }

      console.log(this.catsByAdId)
    })
  }

  ngOnInit() {
    this.updateLists();
  }
  
  deleteAditional(id: number) {
    if (confirm(
      '¿Estás seguro de que deseas eliminar este adicional?',
    )) {
      this.aditionalService.delete(id).subscribe(() => {
        this.updateLists();
      })
    }
  }

  editAditional(id: number) {
    this.router.navigate([`/aditional/${id}/edit`])
  }

  createAditional() {
    this.router.navigate(["/aditional/create"])
  }
}
