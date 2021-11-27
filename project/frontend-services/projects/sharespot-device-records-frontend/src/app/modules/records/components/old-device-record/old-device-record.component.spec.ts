import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OldDeviceRecordComponent } from './old-device-record.component';

describe('OldDeviceRecordComponent', () => {
  let component: OldDeviceRecordComponent;
  let fixture: ComponentFixture<OldDeviceRecordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OldDeviceRecordComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OldDeviceRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
