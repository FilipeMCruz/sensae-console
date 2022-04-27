import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {DeviceInformation} from "@frontend-services/device-management/model";
import {DeviceRecordsInput} from "@frontend-services/device-management/dto";
import {DeviceRecordRegisterMapper} from "@frontend-services/device-management/mapper";

@Injectable({
  providedIn: 'root',
})
export class IndexDeviceRecord {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  index(event: DeviceInformation): Observable<DeviceInformation> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["device_management:device:edit"]))
      return EMPTY;

    const mutation = gql`
      mutation index($records: DeviceRecordsInput) {
        index(records: $records) {
          device {
            id
            name
            downlink
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
          commands {
            id
            name
            ref
            port
            payload
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
