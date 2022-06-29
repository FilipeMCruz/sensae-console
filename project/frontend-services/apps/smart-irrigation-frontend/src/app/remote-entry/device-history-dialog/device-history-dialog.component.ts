import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {
  ChartConfigs,
  Data, DeviceType,
  HistoryQueryFilters, ParkSensorDataHistoryDetails,
  SensorDataHistory,
  SensorDataHistoryDetails, StoveSensorDataHistoryDetails
} from "@frontend-services/smart-irrigation/model";
import {FetchHistory} from "@frontend-services/smart-irrigation/services";

@Component({
  selector: 'frontend-services-device-history-dialog',
  templateUrl: 'device-history-dialog.component.html',
  styleUrls: ['./device-history-dialog.component.scss'],
})
export class DeviceHistoryDialogComponent implements OnInit {
  options: any;

  constructor(
    private fetchHistoryService: FetchHistory,
    public dialogRef: MatDialogRef<DeviceHistoryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Data,
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.fetchHistoryService.getData(HistoryQueryFilters.defaultDevice(this.data.device.id))
      .subscribe(
        next => this.extractData(next[0])
      );
  }

  private extractData(data: SensorDataHistory) {
    const deviceType = data.type;
    let measure1config = ChartConfigs.getIlluminanceConfig();
    let measure2config = ChartConfigs.getSoilMoistureConfig();
    if (deviceType === DeviceType.STOVE_SENSOR) {
      measure1config = ChartConfigs.getTemperatureConfig();
      measure2config = ChartConfigs.getAirHumidityConfig();
    }

    const measure1 = data.ledger.flatMap(entry => entry.data).map((details: SensorDataHistoryDetails) => {
      if (deviceType === DeviceType.PARK_SENSOR) {
        return [details.reportedAt, (details as ParkSensorDataHistoryDetails).illuminance.lux];
      } else {
        return [details.reportedAt, (details as StoveSensorDataHistoryDetails).temperature.celsius];
      }
    }).sort((a, b) => a[0].valueOf() - b[0].valueOf());
    const measure2 = data.ledger.flatMap(entry => entry.data).map((details: SensorDataHistoryDetails) => {
      if (deviceType === DeviceType.PARK_SENSOR) {
        return [details.reportedAt, (details as ParkSensorDataHistoryDetails).soilMoisture.relativePercentage];
      } else {
        return [details.reportedAt, (details as StoveSensorDataHistoryDetails).humidity.relativePercentage];
      }
    }).sort((a, b) => a[0].valueOf() - b[0].valueOf());

    this.options = {
      legend: {
        data: [measure1config.name, measure2config.name],
        align: 'left',
      },
      dataZoom: [
        {
          type: 'inside',
          start: 80,
          end: 100
        },
        {
          start: 80,
          end: 100
        }
      ], tooltip: {
        trigger: 'axis',
        axisPointer: {type: 'cross'}
      },
      xAxis: {
        type: 'time',
        boundaryGap: false
      },
      yAxis: [{
        type: 'value',
        name: measure1config.name,
        min: measure1config.minValue,
        max: measure1config.maxValue,
        axisLine: {},
        axisLabel: {
          formatter: '{value} ' + measure1config.units
        },
        boundaryGap: [0, '100%']
      }, {
        type: 'value',
        name: measure2config.name,
        min: measure2config.minValue,
        max: measure2config.maxValue,
        axisLine: {},
        axisLabel: {
          formatter: '{value} ' + measure2config.units
        },
        boundaryGap: [0, '100%']
      }],
      series: [
        {
          name: measure1config.name,
          type: 'line',
          smooth: true,
          symbol: 'none',
          areaStyle: {},
          color: measure1config.color,
          data: measure1,
          yAxisIndex: 0,
          animationDelay: (idx: number) => idx * 10,
        },
        {
          name: measure2config.name,
          type: 'line',
          smooth: true,
          symbol: 'none',
          areaStyle: {},
          color: measure2config.color,
          data: measure2,
          yAxisIndex: 1,
          animationDelay: (idx: number) => idx * 10 + 100,
        },
      ],
      animationEasing: 'elasticOut',
      animationDelayUpdate: (idx: number) => idx * 5,
    }
  }
}
