import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlateMenuComponent } from './plate-menu.component';

describe('PlateMenuComponent', () => {
  let component: PlateMenuComponent;
  let fixture: ComponentFixture<PlateMenuComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlateMenuComponent]
    });
    fixture = TestBed.createComponent(PlateMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
