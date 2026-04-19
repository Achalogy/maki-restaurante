import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Operator } from 'src/app/interfaces/operator.interface';
import { OperatorService } from 'src/app/service/data/operator.service';

@Component({
  selector: 'app-operator-crud',
  templateUrl: './operator-crud.component.html',
  styleUrls: ['./operator-crud.component.css']
})
export class OperatorCrudComponent {

  operatorList: Operator[] = []

  constructor(
    private operatorService: OperatorService,
    private router: Router
  ) {

  }

  ngOnInit() {
    this.operatorService.selectAll().subscribe(operators => this.operatorList = operators)
  }

  createOperator(): void {
    this.router.navigate([`/operator/create`])
  }

  goToOperator(id: number): void {
    this.router.navigate([`/operator/${id}`])
  }

  editOperator(id: number): void {
    this.router.navigate([`/operator/${id}/edit`])
  }

  deleteOperatorById(id: number): void {
    if (confirm(
      '¿Estás seguro de que deseas eliminar este operario?',
    )) {
      this.operatorService.delete(id).subscribe(() => {
        this.operatorService.selectAll().subscribe(operators => this.operatorList = operators)
      })
    }

  }
}
