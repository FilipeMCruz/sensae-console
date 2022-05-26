import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleManagementComponent } from './rule-management.component';

describe('NewDeviceRecordComponent', () => {
  let component: RuleManagementComponent;
  let fixture: ComponentFixture<RuleManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RuleManagementComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RuleManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
