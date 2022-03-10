import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { DeviceDTO, DeviceRecordDelete } from '../dtos/RecordsDTO';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { HttpHeaders } from '@angular/common/http';
import { extract, isNonNull } from './ObservableFunctions';
import { filter, map } from 'rxjs/operators';
import { DeviceMapper } from '../mappers/DeviceMapper';
import { Device } from '../model/Device';
import { DeviceRecord } from '../model/DeviceRecord';

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
