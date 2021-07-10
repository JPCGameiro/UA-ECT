import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchDivComponent } from './search-div.component';

describe('SearchDivComponent', () => {
  let component: SearchDivComponent;
  let fixture: ComponentFixture<SearchDivComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchDivComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchDivComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
