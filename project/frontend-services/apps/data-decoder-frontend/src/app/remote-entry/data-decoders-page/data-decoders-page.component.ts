import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AuthService} from "@frontend-services/simple-auth-lib";
import {DataDecoderDialogComponent} from "../data-decoder-dialog/data-decoder-dialog.component";
import {DeleteDataDecoder, GetAllDataDecoders, IndexDataDecoder} from "@frontend-services/data-decoder/services";
import {DataDecoder, DataDecoderPair, DataDecoderViewType, SensorTypeId} from "@frontend-services/data-decoder/model";
import {DateFormat} from "@frontend-services/core";

const DAY = 86400;

const HOUR = 3600;

const TEN_MINUTES = 600;

@Component({
  selector: 'frontend-services-data-decoders-page',
  templateUrl: './data-decoders-page.component.html',
  styleUrls: ['./data-decoders-page.component.scss'],
})
export class DataDecodersPageComponent implements OnInit {
  dataDecoders: Array<DataDecoder> =
    new Array<DataDecoder>();

  dataDecoderViewType = DataDecoderViewType;
  loading = true;

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
    this.loading = true;
    this.collector
      .getData()
      .subscribe(
        (data: Array<DataDecoder>) => (this.dataDecoders = data.sort((a, b) => a.data.type.localeCompare(b.data.type))),
        error => error,
        () => this.loading = false);
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
    return this.authService.isAllowed(Array.of("data_decoders:decoders:edit"));
  }

  canDelete() {
    return this.authService.isAllowed(Array.of("data_decoders:decoders:delete"));
  }

  getLastTimeSeen(decoder: DataDecoder) {
    const seconds = Math.floor((new Date().valueOf() - decoder.lastTimeSeen.valueOf()) / 1000);

    const interval = seconds / 31536000;
    if (interval > 1) {
      return "never";
    }

    return DateFormat.timeAgo(decoder.lastTimeSeen) + " ago";
  }

  getLastTimeSeenColor(transformation: DataDecoder) {
    const seconds = Math.floor((new Date().valueOf() - transformation.lastTimeSeen.valueOf()) / 1000);
    if (seconds >= DAY) return "red-chip";
    else if (seconds >= HOUR) return "orange-chip";
    else if (seconds >= TEN_MINUTES) return "yellow-chip";
    else return "green-chip";
  }
}
