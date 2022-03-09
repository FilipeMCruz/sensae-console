import { Injectable } from '@angular/core';
import { FetchResult } from '@apollo/client/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { DeviceRecordsInput } from '../dtos/RecordsDTO';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '@frontend-services/simple-auth-lib';

@Injectable({
  providedIn: 'root',
})
export class IndexDeviceRecord {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  index(
    records: DeviceRecordsInput
  ): Observable<FetchResult<DeviceRecordsInput>> {
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
    return this.apollo.use('deviceRecords').mutate<DeviceRecordsInput>({
      mutation,
      context: {
        headers: new HttpHeaders().set(
          'Authorization',
          'Bearer ' + this.auth.getToken()
        ),
      },
      variables: { records: records.index },
    });
  }
}
