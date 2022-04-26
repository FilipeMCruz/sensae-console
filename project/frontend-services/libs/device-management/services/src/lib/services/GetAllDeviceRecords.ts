import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {DeviceRecord} from '@frontend-services/device-management/model';
import {DeviceRecordQuery} from '@frontend-services/device-management/dto';
import {DeviceRecordsQueryMapper} from '@frontend-services/device-management/mapper';

@Injectable({
  providedIn: 'root',
})
export class GetAllDeviceRecords {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(): Observable<Array<DeviceRecord>> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["device_records:records:read"]))
      return EMPTY;

    const query = gql`
      query deviceRecords {
        deviceRecords {
          device {
            id
            name
          }
          entries {
            label
            content
            type
          }
          subDevices {
            id
            ref
          }
        }
      }
    `;
    return this.apollo
      .use('deviceRecords')
      .query<DeviceRecordQuery>({
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
        map((data: DeviceRecordQuery) =>
          DeviceRecordsQueryMapper.dtoToModel(data)
        )
      );
  }
}
