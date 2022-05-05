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
    this.demoData();
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
        return [details.reportedAt, (details as ParkSensorDataHistoryDetails).soilMoisture.percentage];
      } else {
        return [details.reportedAt, (details as StoveSensorDataHistoryDetails).humidity.gramsPerCubicMeter];
      }
    }).sort((a, b) => a[0].valueOf() - b[0].valueOf());

    console.log(measure2)

    this.options = {
      legend: {
        data: [measure1config.name, measure2config.name],
        align: 'left',
      },
      dataZoom: [
        {
          type: 'inside',
          start: 0,
          end: 20
        },
        {
          start: 0,
          end: 20
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

  private demoData() {
    let base = +new Date(1988, 9, 3);
    const oneDay = 24 * 3600 * 1000;

    const data1 = [];
    const data2 = [];
    for (let i = 0; i < 100; i++) {
      const now = new Date((base += oneDay));
      data1.push([now, (Math.sin(i / 5) * (i / 5 - 10) + i / 6) * 2]);
      data2.push([now, (Math.cos(i / 5) * (i / 5 - 10) + i / 6) * 2]);
    }

    this.options = {
      legend: {
        data: ['Temperature', 'Air Humidity'],
        align: 'left',
      },
      dataZoom: [
        {
          type: 'inside',
          start: 0,
          end: 20
        },
        {
          start: 0,
          end: 20
        }
      ],
      tooltip: {},
      xAxis: {
        type: 'time',
        boundaryGap: false
      },
      yAxis: [{
        type: 'value',
        name: "Temperature",
        min: -20,
        max: 60,
        axisLine: {},
        axisLabel: {
          formatter: '{value} ºC'
        },
        boundaryGap: [0, '100%']
      }, {
        type: 'value',
        name: "Air Humidity",
        min: 0,
        max: 80,
        axisLine: {},
        axisLabel: {
          formatter: '{value} g/cm3'
        },
        boundaryGap: [0, '100%']
      }],
      series: [
        {
          name: 'Temperature',
          type: 'line',
          smooth: true,
          symbol: 'none',
          areaStyle: {},
          data: data1,
          color: "#34a0a4",
          animationDelay: (idx: number) => idx * 10,
        },
        {
          name: 'Air Humidity',
          type: 'line',
          smooth: true,
          symbol: 'none',
          areaStyle: {},
          data: data2,
          color: "#ee9b00",
          animationDelay: (idx: number) => idx * 10 + 100,
        },
      ],
      animationEasing: 'elasticOut',
      animationDelayUpdate: (idx: number) => idx * 5,
    };
  }
}
