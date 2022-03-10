import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {DeviceRecordQuery} from '../dtos/RecordsDTO';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {filter, map} from "rxjs/operators";
import {DeviceRecord} from "../model/DeviceRecord";
import {extract, isNonNull} from "./ObservableFunctions";
import {DeviceRecordsQueryMapper} from "../mappers/DeviceRecordsQueryMapper";

@Injectable({
  providedIn: 'root',
})
export class GetAllDeviceRecords {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(): Observable<Array<DeviceRecord>> {
    const query = gql`
      query deviceRecords {
        deviceRecords {
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
    return this.apollo.use('deviceRecords').query<DeviceRecordQuery>({
      query,
      context: {
        headers: new HttpHeaders().set(
          'Authorization',
          'Bearer ' + this.auth.getToken()
        ),
      },
      fetchPolicy: 'no-cache',
    })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: DeviceRecordQuery) => DeviceRecordsQueryMapper.dtoToModel(data))
      );
  }
}
