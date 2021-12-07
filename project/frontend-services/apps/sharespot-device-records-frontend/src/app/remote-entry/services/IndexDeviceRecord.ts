import {Injectable} from '@angular/core';
import {FetchResult} from '@apollo/client/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {DeviceRecordIndex, DeviceRecordsInput} from '../dtos/RecordsDTO';

@Injectable({
  providedIn: 'root'
})
export class IndexDeviceRecord {

  constructor(private apollo: Apollo) {
  }

  index(records: DeviceRecordsInput): Observable<FetchResult<DeviceRecordsInput>> {
    console.log(records)
    const mutation = gql`
      mutation index($records: DeviceRecordsInput){
        index(records: $records){
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
      .mutate<DeviceRecordsInput>({mutation, variables: {records: records.index}});
  }
}
