import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Operator } from 'src/app/interfaces/operator.interface'; 
import { OperatorService } from 'src/app/service/data/operator.service';

@Component({
  selector: 'app-operator-form',
  templateUrl: './operator-form.component.html',
  styleUrls: ['./operator-form.component.css']
})
export class OperatorFormComponent {

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

      this.operatorService.selectById(+id).subscribe(found => {
        if(found)
          this.operator = found
        else 
          this.router.navigate(['/operator/crud']);
      })

    }
  }

  onSubmit(): void {
    if (this.modo === 'crear') {
      this.operatorService.create(this.operator).subscribe(() => {
        this.router.navigate(['/operator/crud']);
      })
    } else {
      this.operatorService.update(+this.operator.id, this.operator).subscribe(() => {
        this.router.navigate(['/operator/crud']);
      })
    }

  }

  cancelar(): void {
    this.router.navigate(['/operator/crud']);
  }
}
