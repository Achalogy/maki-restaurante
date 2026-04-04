import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Operator } from '../../interfaces/operator.interface'; 
import {OperatorService} from '../../service/operator.service';

@Component({
  selector: 'app-operator-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './operator-form.component.html',
  styleUrls: ['./operator-form.component.css']
})
export class OperatorFormComponent implements OnInit {

  modo: 'crear' | 'editar' = 'crear';

  operator: Operator = {
    id: 0,
    name: '',
    username: '',
    password: ''
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private operatorService: OperatorService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.modo = 'editar';
      const encontrado = this.operatorService.selectById(+id);
      if (encontrado) {
        this.operator = { ...encontrado };
      } else {
        this.router.navigate(['/operator']);
      }
    }
  }

  onSubmit(): void {
    if (this.modo === 'crear') {
      this.operatorService.create(this.operator);
    } else {
      this.operatorService.update(+this.operator.id, this.operator);
    }

    this.router.navigate(['/operator']);
  }

  cancelar(): void {
    this.router.navigate(['/operator']);
  }
}
