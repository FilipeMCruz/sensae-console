import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {HttpHeaders} from '@angular/common/http';
import {filter, map} from 'rxjs/operators';
import {Device, DeviceInformation} from '@frontend-services/device-management/model';
import {DeviceRecordDelete} from '@frontend-services/device-management/dto';
import {DeviceMapper} from '@frontend-services/device-management/mapper';
import {extract, isNonNull} from "@frontend-services/core";

@Injectable({
  providedIn: 'root',
})
export class DeleteDeviceInformation {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo() {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["device_management:device:delete"])
  }

  delete(event: DeviceInformation): Observable<Device> {
    if (!this.canDo())
      return EMPTY;

    const mutation = gql`
      mutation delete($device: DeviceInput) {
        delete(device: $device) {
          id
          name
        }
      }
    `;
    return this.apollo
      .use('deviceInformation')
      .mutate<DeviceRecordDelete>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: {device: DeviceMapper.modelToDto(event.device)},
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
