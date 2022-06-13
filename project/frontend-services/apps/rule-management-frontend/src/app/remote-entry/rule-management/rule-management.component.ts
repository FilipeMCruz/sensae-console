import {Component, EventEmitter, Input, OnChanges, Output,} from '@angular/core';
import {RuleScenario, RuleScenarioViewType} from "@frontend-services/rule-management/model";

@Component({
  selector: 'frontend-services-rule-management',
  templateUrl: './rule-management.component.html',
  styleUrls: ['./rule-management.component.scss'],
})
export class RuleManagementComponent implements OnChanges {
  @Input() entry!: RuleScenario;
  @Input() ruleScenarioViewType!: RuleScenarioViewType;
  @Input() canEdit = false;
  @Input() canDelete = false;

  @Output() newRuleScenarioEvent = new EventEmitter<RuleScenario>();
  @Output() deleteRuleScenarioEvent =
    new EventEmitter<RuleScenario>();

  ruleScenario = RuleScenario.empty();

  ruleScenarioView = RuleScenarioViewType.New;
  scenarioViewType = RuleScenarioViewType;

  ngOnChanges(): void {
    if (this.entry) {
      this.ruleScenarioView = RuleScenarioViewType.Edit;
      this.ruleScenario = this.entry.copy();
    }
    if (this.ruleScenarioViewType) {
      this.ruleScenarioView = this.ruleScenarioViewType;
    }
  }

  saveRuleScenario() {
    this.newRuleScenarioEvent.emit(this.ruleScenario);
  }

  deleteRuleScenario() {
    this.deleteRuleScenarioEvent.emit(this.ruleScenario);
  }
}
