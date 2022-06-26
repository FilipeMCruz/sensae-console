import {Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild,} from '@angular/core';
import {DataDecoder, DataDecoderViewType} from "@frontend-services/data-decoder/model";
import {first} from "rxjs/operators";
import {MonacoEditorService} from "@frontend-services/mutual";

declare let monaco: any;

@Component({
  selector: 'frontend-services-data-decoder',
  templateUrl: './data-decoder.component.html',
  styleUrls: ['./data-decoder.component.scss'],
})
export class DataDecoderComponent implements OnChanges, OnInit {
  @Input() entry!: DataDecoder;
  @Input() dataDecoderViewEntry!: DataDecoderViewType;
  @Input() canEdit = false;
  @Input() canDelete = false;

  @Output() newDataDecoderEvent = new EventEmitter<DataDecoder>();
  @Output() deleteDataDecoderEvent =
    new EventEmitter<DataDecoder>();

  public _editor: any;
  @ViewChild('editorContainer', {static: true})
  _editorContainer!: ElementRef;

  dataDecoder = DataDecoder.empty();

  dataDecoderView = DataDecoderViewType.New;
  dataDecoderViewType = DataDecoderViewType;

  constructor(public monacoEditorService: MonacoEditorService) {
    this.monacoEditorService.load();
  }

  ngOnInit() {
    this.initMonaco();
  }

  ngOnChanges(): void {
    if (this.entry) {
      this.dataDecoderView = DataDecoderViewType.Edit;
      this.dataDecoder = this.entry;
    }
    if (this.dataDecoderViewEntry) {
      this.dataDecoderView = this.dataDecoderViewEntry;
    }
  }

  saveDataDecoder() {
    this.newDataDecoderEvent.emit(this.dataDecoder);
    this.resetView();
  }

  deleteDataDecoder() {
    this.deleteDataDecoderEvent.emit(this.dataDecoder);
    this.resetView();
  }

  private resetView() {
    this.dataDecoderView = this.dataDecoderViewType.New;
    this.dataDecoder = DataDecoder.empty();
  }

  private initMonaco(): void {
    if (!this.monacoEditorService.loaded) {
      this.monacoEditorService.loadingFinished.pipe(first()).subscribe(() => {
        this.initMonaco();
        this._editor.onDidChangeModelContent(() => this.dataDecoder.script.content = this._editor.getValue())
      });
      return;
    }

    this._editor = monaco.editor.create(
      this._editorContainer.nativeElement, {
        value: this.dataDecoder.script.content,
        language: 'javascript',
        readOnly: this.dataDecoderView === this.dataDecoderViewType.Compare || !this.canEdit
      });
  }
}
