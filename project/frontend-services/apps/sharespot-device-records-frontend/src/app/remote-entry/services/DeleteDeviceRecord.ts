import { Injectable } from '@angular/core';
import { FetchResult } from '@apollo/client/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { DeviceDTO, DeviceRecordDelete } from '../dtos/RecordsDTO';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class DeleteDeviceRecord {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  delete(data: DeviceDTO): Observable<FetchResult<DeviceRecordDelete>> {
    const mutation = gql`
      mutation delete($device: DeviceInput) {
        delete(device: $device) {
          id
          name
        }
      }
    `;
    return this.apollo.use('deviceRecords').mutate<DeviceRecordDelete>({
      mutation,
      context: {
        headers: new HttpHeaders().set(
          'Authorization',
          'Bearer ' + this.auth.getToken()
        ),
      },
      variables: { device: data },
    });
  }
}
