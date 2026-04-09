import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientSessionComponent } from './client-session.component';

describe('ClientSessionComponent', () => {
  let component: ClientSessionComponent;
  let fixture: ComponentFixture<ClientSessionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClientSessionComponent]
    });
    fixture = TestBed.createComponent(ClientSessionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
