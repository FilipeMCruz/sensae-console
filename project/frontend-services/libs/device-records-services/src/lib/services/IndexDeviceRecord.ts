import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {DeviceRecord} from '@frontend-services/device-records-model';
import {DeviceRecordsInput} from '@frontend-services/device-records-dto';
import {DeviceRecordRegisterMapper} from '@frontend-services/device-records-mapper';

@Injectable({
  providedIn: 'root',
})
export class IndexDeviceRecord {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  index(event: DeviceRecord): Observable<DeviceRecord> {
    const mutation = gql`
      mutation index($records: DeviceRecordsInput) {
        index(records: $records) {
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
      .mutate<DeviceRecordsInput>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: {
          records: DeviceRecordRegisterMapper.modelToDto(event).index,
        },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((value: DeviceRecordsInput) =>
          DeviceRecordRegisterMapper.dtoToModel(value)
        )
      );
  }
}
