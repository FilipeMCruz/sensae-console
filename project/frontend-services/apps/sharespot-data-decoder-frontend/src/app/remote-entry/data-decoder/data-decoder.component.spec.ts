import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataDecoderComponent } from './data-decoder.component';

describe('NewDeviceRecordComponent', () => {
  let component: DataDecoderComponent;
  let fixture: ComponentFixture<DataDecoderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DataDecoderComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DataDecoderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
