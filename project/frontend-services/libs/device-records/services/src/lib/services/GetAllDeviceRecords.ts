import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {DeviceRecord} from '@frontend-services/device-records/model';
import {DeviceRecordQuery} from '@frontend-services/device-records/dto';
import {DeviceRecordsQueryMapper} from '@frontend-services/device-records/mapper';

@Injectable({
  providedIn: 'root',
})
export class GetAllDeviceRecords {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(): Observable<Array<DeviceRecord>> {
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