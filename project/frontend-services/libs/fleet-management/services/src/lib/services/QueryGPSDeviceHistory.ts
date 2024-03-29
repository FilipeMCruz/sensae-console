import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {GPSSensorDataQuery, HistorySensorDTO,} from '@frontend-services/fleet-management/dto';
import {DeviceHistory} from '@frontend-services/fleet-management/model';
import {DevicePastDataMapper} from '@frontend-services/fleet-management/mapper';

@Injectable({
  providedIn: 'root',
})
export class QueryGPSDeviceHistory {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(filters: GPSSensorDataQuery): Observable<Array<DeviceHistory>> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["fleet_management:past_data:read"]))
      return EMPTY;

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
        variables: {filters},
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: HistorySensorDTO) => DevicePastDataMapper.dtoToModel(data))
      );
  }
}
