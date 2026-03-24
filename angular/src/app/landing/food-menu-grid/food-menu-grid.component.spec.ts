import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FoodMenuGridComponent } from './food-menu-grid.component';

describe('FoodMenuGridComponent', () => {
  let component: FoodMenuGridComponent;
  let fixture: ComponentFixture<FoodMenuGridComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FoodMenuGridComponent]
    });
    fixture = TestBed.createComponent(FoodMenuGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
