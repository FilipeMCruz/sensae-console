import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { GPSSensorLatestData } from '../dtos/SensorDTO';
import { Injectable } from '@angular/core';
import { filter, map } from 'rxjs/operators';
import { extract, isNonNull } from './ObservableFunctions';
import { DeviceLiveDataMapper } from '../mappers/DeviceLiveDataMapper';
import { DeviceData } from '../model/livedata/DeviceData';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '@frontend-services/simple-auth-lib';

@Injectable({
  providedIn: 'root',
})
export class QueryLatestGPSDeviceData {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  getData(): Observable<DeviceData[]> {
    const query = gql`
      query latest {
        latest {
          dataId
          device {
            id
            name
            records {
              label
              content
            }
          }
          reportedAt
          data {
            gps {
              longitude
              latitude
            }
            status {
              motion
            }
          }
        }
      }
    `;

    return this.apollo
      .use('fleetManagement')
      .subscribe<GPSSensorLatestData>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: GPSSensorLatestData) =>
          data.latest.map((s) => DeviceLiveDataMapper.dtoToModel(s))
        )
      );
  }
}
