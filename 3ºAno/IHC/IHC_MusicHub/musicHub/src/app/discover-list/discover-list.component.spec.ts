import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiscoverListComponent } from './discover-list.component';

describe('DiscoverListComponent', () => {
  let component: DiscoverListComponent;
  let fixture: ComponentFixture<DiscoverListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiscoverListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DiscoverListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
