import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientLogInComponent } from './client-log-in.component';

describe('ClientLogInComponent', () => {
  let component: ClientLogInComponent;
  let fixture: ComponentFixture<ClientLogInComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClientLogInComponent]
    });
    fixture = TestBed.createComponent(ClientLogInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
