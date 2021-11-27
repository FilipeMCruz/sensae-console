import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewRecordEntryComponent } from './new-record-entry.component';

describe('NewRecordEntryComponent', () => {
  let component: NewRecordEntryComponent;
  let fixture: ComponentFixture<NewRecordEntryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewRecordEntryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewRecordEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
