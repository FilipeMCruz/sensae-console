import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import {RuleScenario, RuleScenarioViewType} from "@frontend-services/rule-management/model";
import {MonacoEditorService} from "@frontend-services/mutual";
import {first} from "rxjs/operators";

declare let monaco: any;

@Component({
  selector: 'frontend-services-rule-management',
  templateUrl: './rule-management.component.html',
  styleUrls: ['./rule-management.component.scss'],
})
export class RuleManagementComponent implements OnChanges, OnInit {
  @Input() entry!: RuleScenario;
  @Input() ruleScenarioViewType!: RuleScenarioViewType;
  @Input() canEdit = false;
  @Input() canDelete = false;

  @Output() newRuleScenarioEvent = new EventEmitter<RuleScenario>();
  @Output() deleteRuleScenarioEvent =
    new EventEmitter<RuleScenario>();

  public _editor: any;
  @ViewChild('editorContainer', {static: true})
  _editorContainer!: ElementRef;

  ruleScenario = RuleScenario.empty();

  ruleScenarioView = RuleScenarioViewType.New;
  scenarioViewType = RuleScenarioViewType;

  constructor(public monacoEditorService: MonacoEditorService, public changeDetectorRef: ChangeDetectorRef) {
    this.monacoEditorService.load();
  }

  ngOnInit() {
    this.initMonaco();
  }

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

  private initMonaco(): void {
    if (!this.monacoEditorService.loaded) {
      this.monacoEditorService.loadingFinished.pipe(first()).subscribe(() => {
        this.initMonaco();
        this._editor.onDidChangeModelContent(() => {
          this.ruleScenario.content.value = this._editor.getValue()
          this.changeDetectorRef.detectChanges();
        })
      });
      return;
    }

    this._editor = monaco.editor.create(
      this._editorContainer.nativeElement, {
        value: this.ruleScenario.content.value,
        language: 'plaintext',
        readOnly: this.ruleScenarioView === this.scenarioViewType.Compare || !this.canEdit
      });
  }
}
