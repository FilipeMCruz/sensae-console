import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { EMPTY, Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { extract, isNonNull } from '@frontend-services/core';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { DeviceData } from '@frontend-services/fleet-management/model';
import { FilteredByContentSensorDTO } from '@frontend-services/fleet-management/dto';
import { DeviceLiveDataMapper } from '@frontend-services/fleet-management/mapper';

@Injectable({
  providedIn: 'root',
})
export class SubscribeToGPSDataByContent {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  getData(content: string): Observable<DeviceData> {
    if (!this.auth.isAuthenticated()) return EMPTY;

    const query = gql`
      subscription locationByContent($content: String, $Authorization: String) {
        locationByContent(content: $content, Authorization: $Authorization) {
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
      .subscribe<FilteredByContentSensorDTO>({
        query,
        variables: { content, Authorization: 'Bearer ' + this.auth.getToken() },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: FilteredByContentSensorDTO) =>
          DeviceLiveDataMapper.dtoToModel(data.locationByContent)
        )
      );
  }
}
