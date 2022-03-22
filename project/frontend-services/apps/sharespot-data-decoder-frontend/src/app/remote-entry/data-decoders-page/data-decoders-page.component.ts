import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AuthService} from "@frontend-services/simple-auth-lib";
import {DataDecoderDialogComponent} from "../data-decoder-dialog/data-decoder-dialog.component";
import {DeleteDataDecoder, GetAllDataDecoders, IndexDataDecoder} from "@frontend-services/data-decoder/services";
import {DataDecoder, DataDecoderPair, DataDecoderViewType, SensorTypeId} from "@frontend-services/data-decoder/model";

@Component({
  selector: 'frontend-services-data-decoders-page',
  templateUrl: './data-decoders-page.component.html',
  styleUrls: ['./data-decoders-page.component.scss'],
})
export class DataDecodersPageComponent implements OnInit {
  dataDecoders: Array<DataDecoder> =
    new Array<DataDecoder>();

  dataDecoderViewType = DataDecoderViewType;

  constructor(
    public dialog: MatDialog,
    private collector: GetAllDataDecoders,
    private indexer: IndexDataDecoder,
    private eraser: DeleteDataDecoder,
    private authService: AuthService
  ) {
  }

  openDialog(data: DataDecoderPair) {
    const dialogRef = this.dialog.open(DataDecoderDialogComponent, {
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
    this.fetchAllDataDecoders();
  }

  fetchAllDataDecoders() {
    this.collector
      .getData()
      .subscribe(
        (data: Array<DataDecoder>) => (this.dataDecoders = data)
      );
  }

  updateItem(event: DataDecoder) {
    this.saveItem(event);
  }

  addItem(event: DataDecoder) {
    const decoders = this.dataDecoders.filter(
      (r) => r.data.type == event.data.type
    );
    if (decoders.length != 0) {
      this.openDialog(
        new DataDecoderPair(event, decoders[0])
      );
    } else {
      this.saveItem(event);
    }
  }

  private saveItem(event: DataDecoder) {
    this.indexer
      .index(event)
      .subscribe((dataTransformation: DataDecoder) => {
        this.dataDecoders = this.dataDecoders.filter(
          (r) => r.data.type != dataTransformation.data.type
        );
        this.dataDecoders.push(dataTransformation);
      });
  }

  deleteItem(event: DataDecoder) {
    this.eraser.delete(event).subscribe((data: SensorTypeId) => {
      this.dataDecoders = this.dataDecoders.filter(
        (r) => r.data.type != data.type
      );
    });
  }

  canEdit() {
    return this.authService.isAllowed(Array.of("data_decoders:decoders:write"));
  }
}
