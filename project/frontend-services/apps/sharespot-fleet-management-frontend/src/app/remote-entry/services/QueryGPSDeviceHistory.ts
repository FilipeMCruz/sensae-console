import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { GPSSensorDataQuery, HistorySensorDTO } from '../dtos/SensorDTO';
import { Injectable } from '@angular/core';
import { filter, map } from 'rxjs/operators';
import { extract, isNonNull } from './ObservableFunctions';
import { DevicePastDataMapper } from '../mappers/DevicePastDataMapper';
import { DeviceHistory } from '../model/pastdata/DeviceHistory';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '@frontend-services/simple-auth-lib';

@Injectable({
  providedIn: 'root',
})
export class QueryGPSDeviceHistory {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  getData(filters: GPSSensorDataQuery): Observable<Array<DeviceHistory>> {
    const query = gql`
      query history($filters: GPSSensorDataQuery) {
        history(filters: $filters) {
          deviceId
          deviceName
          startTime
          endTime
          distance
          segments {
            type
            steps {
              status {
                motion
              }
              reportedAt
              gps {
                longitude
                latitude
              }
            }
          }
        }
      }
    `;

    return this.apollo
      .use('fleetManagement')
      .subscribe<HistorySensorDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: { filters },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: HistorySensorDTO) => DevicePastDataMapper.dtoToModel(data))
      );
  }
}
