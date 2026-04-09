import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Operator } from 'src/app/interfaces/operator.interface';
import { OperatorService } from 'src/app/service/operator.service';

@Component({
  selector: 'app-operator-view',
  templateUrl: './operator-view.component.html',
  styleUrls: ['./operator-view.component.css']
})
export class OperatorViewComponent {
  operator: Operator | undefined;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private operatorService: OperatorService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    
    this.operator = this.operatorService.selectById(id)
  }

  editOperator() {
    this.router.navigate([`/operator/${this.operator?.id}/edit`])
  }

  backToCrud() {
    this.router.navigate([`/operator/crud`])
  }
}
