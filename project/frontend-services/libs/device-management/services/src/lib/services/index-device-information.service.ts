import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {DeviceInformation} from "@frontend-services/device-management/model";
import {DeviceRecordsInputResult} from "@frontend-services/device-management/dto";
import {DeviceRecordRegisterMapper} from "@frontend-services/device-management/mapper";

@Injectable({
  providedIn: 'root',
})
export class IndexDeviceInformation {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo() {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["device_management:device:edit"])
  }

  index(event: DeviceInformation): Observable<DeviceInformation> {
    if (!this.canDo())
      return EMPTY;

    const mutation = gql`
      mutation index($instructions: DeviceInformationInput) {
        index(instructions: $instructions) {
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
      .mutate<DeviceRecordsInputResult>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: {
          instructions: DeviceRecordRegisterMapper.modelToDto(event).index,
        },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((value: DeviceRecordsInputResult) =>
          DeviceRecordRegisterMapper.dtoToModel(value)
        )
      );
  }
}
