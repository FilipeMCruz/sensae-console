import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {DeviceData} from '@frontend-services/fleet-management/model';
import {FilteredSensorDTO} from '@frontend-services/fleet-management/dto';
import {DeviceLiveDataMapper} from '@frontend-services/fleet-management/mapper';

@Injectable({
  providedIn: 'root',
})
export class SubscribeToGPSDataByDevice {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(devices: Array<string>): Observable<DeviceData> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["fleet_management:live_data:read"]))
      return EMPTY;

    const query = gql`
      subscription location($devices: [String], $Authorization: String) {
        location(devices: $devices, Authorization: $Authorization) {
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
      .subscribe<FilteredSensorDTO>({
        query,
        variables: {devices, Authorization: 'Bearer ' + this.auth.getToken()},
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: FilteredSensorDTO) =>
          DeviceLiveDataMapper.dtoToModel(data.location)
        )
      );
  }
}
