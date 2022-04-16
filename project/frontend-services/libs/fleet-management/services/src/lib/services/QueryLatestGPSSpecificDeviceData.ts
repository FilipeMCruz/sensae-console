import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {DeviceData} from '@frontend-services/fleet-management/model';
import {FilteredByDeviceGPSSensorLatestData} from '@frontend-services/fleet-management/dto';
import {DeviceLiveDataMapper} from '@frontend-services/fleet-management/mapper';

@Injectable({
  providedIn: 'root',
})
export class QueryLatestGPSSpecificDeviceData {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(devices: Array<string>): Observable<DeviceData[]> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["fleet_management:latest_data:read"]))
      return EMPTY;

    const query = gql`
      query latestByDevice($devices: [String]) {
        latestByDevice(devices: $devices) {
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
      .subscribe<FilteredByDeviceGPSSensorLatestData>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: {devices},
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: FilteredByDeviceGPSSensorLatestData) =>
          data.latestByDevice.map((s) => DeviceLiveDataMapper.dtoToModel(s))
        )
      );
  }
}
