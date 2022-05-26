import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleManagementPageComponent } from './rule-management-page.component';

describe('SimpleAuthPageComponent', () => {
  let component: RuleManagementPageComponent;
  let fixture: ComponentFixture<RuleManagementPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RuleManagementPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RuleManagementPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
