import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenssegerPageComponent } from './mensseger-page.component';

describe('MenssegerPageComponent', () => {
  let component: MenssegerPageComponent;
  let fixture: ComponentFixture<MenssegerPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenssegerPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MenssegerPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
