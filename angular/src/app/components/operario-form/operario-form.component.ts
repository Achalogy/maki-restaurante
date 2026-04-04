import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Operator } from '../../interfaces/operator.interface'; 
import { OPERARIOS } from '../../data/operarios.data';

@Component({
  selector: 'app-operario-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './operario-form.component.html',
  styleUrls: ['./operario-form.component.css']
})
export class OperarioFormComponent implements OnInit {

  modo: 'crear' | 'editar' = 'crear';

  operario: Operator = {
    id: 0,
    name: '',
    username: '',
    password: ''
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.modo = 'editar';
      const encontrado = OPERARIOS.find(o => o.id === +id);
      if (encontrado) {
        this.operario = { ...encontrado };
      }
    }
  }

  onSubmit(): void {
    if (this.modo === 'crear') {
      console.log('Crear operario:', this.operario);
      alert(`Operario "${this.operario.name}" creado (simulado).`);
    } else {
      console.log('Actualizar operario:', this.operario);
      alert(`Operario "${this.operario.name}" actualizado (simulado).`);
    }

    this.router.navigate(['/operarios']);
  }

  cancelar(): void {
    this.router.navigate(['/operarios']);
  }
}
