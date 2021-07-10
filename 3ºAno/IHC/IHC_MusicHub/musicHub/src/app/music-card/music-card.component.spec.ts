import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MusicCardComponent } from './music-card.component';

describe('MusicCardComponent', () => {
  let component: MusicCardComponent;
  let fixture: ComponentFixture<MusicCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MusicCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MusicCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
