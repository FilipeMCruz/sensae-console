import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {DeviceRecordQuery} from '../dtos/RecordsDTO';
import {FetchResult} from '@apollo/client/core';

@Injectable({
  providedIn: 'root'
})
export class GetAllDeviceRecords {

  constructor(private apollo: Apollo) {
  }

  getData(): Observable<FetchResult<DeviceRecordQuery>> {
    const query = gql`
      query deviceRecords{
        deviceRecords{
          device{
            id
            name
          }
          entries{
            label
            content
            type
          }
        }
      }
    `;
    return this.apollo.use("deviceRecords")
      .query<DeviceRecordQuery>({query, fetchPolicy: "no-cache"});
  }
}
