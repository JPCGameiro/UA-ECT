import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MessengercontactComponent } from './messengercontact.component';

describe('MessengercontactComponent', () => {
  let component: MessengercontactComponent;
  let fixture: ComponentFixture<MessengercontactComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MessengercontactComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MessengercontactComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
