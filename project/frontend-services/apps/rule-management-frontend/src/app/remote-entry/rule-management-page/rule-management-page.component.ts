import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {
  RuleScenario,
  RuleScenarioId,
  RuleScenarioPair,
  RuleScenarioViewType
} from "@frontend-services/rule-management/model";
import {DeleteRuleScenario, GetAllRuleScenario, IndexRuleScenario} from "@frontend-services/rule-management/services";
import {RuleManagementDialogComponent} from "../rule-management-dialog/rule-management-dialog.component";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'frontend-services-rule-management-page',
  templateUrl: './rule-management-page.component.html',
  styleUrls: ['./rule-management-page.component.scss'],
})
export class RuleManagementPageComponent implements OnInit {

  ruleScenarios: Array<RuleScenario> =
    new Array<RuleScenario>();

  ruleScenarioViewType = RuleScenarioViewType;

  loading = true;
  emptyScenario = RuleScenario.empty();

  constructor(
    public dialog: MatDialog,
    private collector: GetAllRuleScenario,
    private indexer: IndexRuleScenario,
    private eraser: DeleteRuleScenario,
    private _snackBar: MatSnackBar
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
          this.emptyScenario = RuleScenario.empty();
        },
        error => this.error(error));
  }

  deleteItem(event: RuleScenario) {
    this.eraser.delete(event).subscribe((data: RuleScenarioId) => {
      this.ruleScenarios = this.ruleScenarios.filter(
        (r) => r.id.value != data.value
      );
    });
  }

  getNextRulePublishTime() {
    const current = new Date();
    if (current.getMinutes() < 30) {
      current.setMinutes(30, 0, 0);
    } else {
      current.setHours(current.getHours() + 1, 0, 0, 0);
    }
    return current;
  }

  canEdit() {
    return this.indexer.canDo();
  }

  canDelete() {
    return this.eraser.canDo();
  }

  error(message: string) {
    return this._snackBar.open(message, undefined, {
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      duration: 10000,
      panelClass: ['critical-snackbar']
    });
  }
}
