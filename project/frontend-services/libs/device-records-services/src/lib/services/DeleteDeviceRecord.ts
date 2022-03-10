import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { HttpHeaders } from '@angular/common/http';
import { extract, isNonNull } from './ObservableFunctions';
import { filter, map } from 'rxjs/operators';
import { Device, DeviceRecord } from '@frontend-services/device-records-model';
import { DeviceRecordDelete } from '@frontend-services/device-records-dto';
import { DeviceMapper } from '@frontend-services/device-records-mapper';

@Injectable({
  providedIn: 'root',
})
export class DeleteDeviceRecord {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  delete(event: DeviceRecord): Observable<Device> {
    const mutation = gql`
      mutation delete($device: DeviceInput) {
        delete(device: $device) {
          id
          name
        }
      }
    `;
    return this.apollo
      .use('deviceRecords')
      .mutate<DeviceRecordDelete>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: { device: DeviceMapper.modelToDto(event.device) },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((value: DeviceRecordDelete) =>
          DeviceMapper.dtoToModel(value.delete)
        )
      );
  }
}
