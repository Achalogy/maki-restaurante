import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FoodMenuGridCardComponent } from './food-menu-grid-card.component';

describe('FoodMenuGridCardComponent', () => {
  let component: FoodMenuGridCardComponent;
  let fixture: ComponentFixture<FoodMenuGridCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FoodMenuGridCardComponent]
    });
    fixture = TestBed.createComponent(FoodMenuGridCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
