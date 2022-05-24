import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {RuleManagementDialogComponent} from "../data-decoder-dialog/rule-management-dialog.component";
import {
  RuleScenario,
  RuleScenarioId,
  RuleScenarioPair,
  RuleScenarioViewType
} from "@frontend-services/rule-management/model";
import {DeleteRuleScenario, GetAllRuleScenario, IndexRuleScenario} from "@frontend-services/rule-management/services";

@Component({
  selector: 'frontend-services-data-decoders-page',
  templateUrl: './rule-management-page.component.html',
  styleUrls: ['./rule-management-page.component.scss'],
})
export class RuleManagementPageComponent implements OnInit {

  ruleScenarios: Array<RuleScenario> =
    new Array<RuleScenario>();

  ruleScenarioViewType = RuleScenarioViewType;

  loading = true;

  constructor(
    public dialog: MatDialog,
    private collector: GetAllRuleScenario,
    private indexer: IndexRuleScenario,
    private eraser: DeleteRuleScenario
  ) {
  }

  openDialog(data: RuleScenarioPair) {
    const dialogRef = this.dialog.open(RuleManagementDialogComponent, {
      width: '1400px',
      data,
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.saveItem(data.fresh);
      }
    });
  }

  ngOnInit(): void {
    this.fetchAllRuleScenarios();
  }

  fetchAllRuleScenarios() {
    this.loading = true;
    this.collector
      .getData()
      .subscribe(
        (data: Array<RuleScenario>) => (this.ruleScenarios = data.sort((a, b) => a.id.value.localeCompare(b.id.value))),
        error => error,
        () => this.loading = false);
  }

  updateItem(event: RuleScenario) {
    this.saveItem(event);
  }

  addItem(event: RuleScenario) {
    const scenarios = this.ruleScenarios.filter(
      (r) => r.id.value == event.id.value
    );
    if (scenarios.length != 0) {
      this.openDialog(
        new RuleScenarioPair(event, scenarios[0])
      );
    } else {
      this.saveItem(event);
    }
  }

  private saveItem(event: RuleScenario) {
    this.indexer
      .index(event)
      .subscribe((ruleScenario: RuleScenario) => {
        this.ruleScenarios = this.ruleScenarios.filter(
          (r) => r.id.value != ruleScenario.id.value
        );
        this.ruleScenarios.push(ruleScenario);
      });
  }

  deleteItem(event: RuleScenario) {
    this.eraser.delete(event).subscribe((data: RuleScenarioId) => {
      this.ruleScenarios = this.ruleScenarios.filter(
        (r) => r.id.value != data.value
      );
    });
  }

  canEdit() {
    return this.indexer.canDo();
  }

  canDelete() {
    return this.eraser.canDo();
  }
}
