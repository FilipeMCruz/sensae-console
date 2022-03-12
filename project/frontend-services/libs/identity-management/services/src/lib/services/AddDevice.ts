import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { filter, map } from 'rxjs/operators';
import { extract, isNonNull } from '@frontend-services/core';
import { DeviceInfo } from '@frontend-services/identity-management/model';
import {
  DeviceMapper,
  QueryMapper,
} from '@frontend-services/identity-management/mapper';
import { AddDeviceResultDTO } from '@frontend-services/identity-management/dto';

@Injectable({
  providedIn: 'root',
})
export class AddDevice {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  mutate(
    deviceId: string,
    domainId: string,
    permission: boolean
  ): Observable<DeviceInfo> {
    const mutation = gql`
      mutation addTenant($instructions: AddTenantToDomain) {
        addTenant(instructions: $instructions) {
          oid
          email
          name
        }
      }
    `;
    return this.apollo
      .use('identity')
      .mutate<AddDeviceResultDTO>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: QueryMapper.toAddDevice(deviceId, domainId, permission),
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((value: AddDeviceResultDTO) =>
          DeviceMapper.dtoToModel(value.addDevice)
        )
      );
  }
}
