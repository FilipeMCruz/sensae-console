import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {DeviceInformation} from '@frontend-services/device-management/model';
import {DeviceInformationQuery} from '@frontend-services/device-management/dto';
import {DeviceInformationQueryMapper} from '@frontend-services/device-management/mapper';

@Injectable({
  providedIn: 'root',
})
export class GetAllDeviceInformation {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo() {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["device_management:device:read"])
  }

  getData(): Observable<Array<DeviceInformation>> {
    if (!this.canDo())
      return EMPTY;

    const query = gql`
      query deviceInformation {
        deviceInformation {
          device {
            id
            name
            downlink
          }
          records {
            label
            content
          }
          staticData {
            label
            content
          }
          subDevices {
            id
            ref
          }
          commands {
            id
            name
            ref
            port
            payload
          }
          lastTimeSeen
        }
      }
    `;
    return this.apollo
      .use('deviceInformation')
      .query<DeviceInformationQuery>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        fetchPolicy: 'no-cache',
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: DeviceInformationQuery) =>
          DeviceInformationQueryMapper.dtoToModel(data)
        )
      );
  }
}
