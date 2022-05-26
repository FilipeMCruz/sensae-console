import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {RuleScenarioPair, RuleScenarioViewType} from "@frontend-services/rule-management/model";

@Component({
  selector: 'frontend-services-rule-management-dialog',
  templateUrl: 'rule-management-dialog.component.html',
  styleUrls: ['./rule-management-dialog.component.scss'],
})
export class RuleManagementDialogComponent {

  ruleScenarioViewType = RuleScenarioViewType;

  constructor(
    public dialogRef: MatDialogRef<RuleManagementDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: RuleScenarioPair
  ) {
  }
}
