import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceRecordPageComponent } from './device-record-page.component';

describe('DeviceRecordPageComponent', () => {
  let component: DeviceRecordPageComponent;
  let fixture: ComponentFixture<DeviceRecordPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeviceRecordPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceRecordPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
