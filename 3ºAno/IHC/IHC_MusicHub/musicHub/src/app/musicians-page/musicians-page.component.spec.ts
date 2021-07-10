import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MusiciansPageComponent } from './musicians-page.component';

describe('MusiciansPageComponent', () => {
  let component: MusiciansPageComponent;
  let fixture: ComponentFixture<MusiciansPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MusiciansPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MusiciansPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
