import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {DeviceInfo} from '@frontend-services/identity-management/model';
import {DeviceMapper, QueryMapper,} from '@frontend-services/identity-management/mapper';
import {RemoveDeviceResultDTO} from '@frontend-services/identity-management/dto';

@Injectable({
  providedIn: 'root',
})
export class RemoveDevice {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  mutate(deviceId: string, domainId: string): Observable<DeviceInfo> {
    const mutation = gql`
      mutation removeDevice($instructions: RemoveDeviceFromDomain) {
        removeDevice(instructions: $instructions) {
          oid
          domains {
            oid
            permission
          }
        }
      }
    `;
    return this.apollo
      .use('identity')
      .mutate<RemoveDeviceResultDTO>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: QueryMapper.toRemoveDevice(deviceId, domainId),
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((value: RemoveDeviceResultDTO) =>
          DeviceMapper.dtoToModel(value.removeDevice)
        )
      );
  }
}
