import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminGatewayButtonComponent } from './admin-gateway-button.component';

describe('AdminGatewayButtonComponent', () => {
  let component: AdminGatewayButtonComponent;
  let fixture: ComponentFixture<AdminGatewayButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdminGatewayButtonComponent]
    });
    fixture = TestBed.createComponent(AdminGatewayButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
