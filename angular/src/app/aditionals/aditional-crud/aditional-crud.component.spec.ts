import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AditionalCrudComponent } from './aditional-crud.component';

describe('AditionalCrudComponent', () => {
  let component: AditionalCrudComponent;
  let fixture: ComponentFixture<AditionalCrudComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AditionalCrudComponent]
    });
    fixture = TestBed.createComponent(AditionalCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
