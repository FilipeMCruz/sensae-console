import {Injectable} from '@angular/core';
import {FetchResult} from '@apollo/client/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {DeviceDTO, DeviceRecordDelete} from '../dtos/RecordsDTO';

@Injectable({
  providedIn: 'root'
})
export class DeleteDeviceRecord {

  constructor(private apollo: Apollo) {
  }

  delete(data: DeviceDTO): Observable<FetchResult<DeviceRecordDelete>> {
    console.log(data)
    const mutation = gql`
      mutation delete($device: DeviceInput){
        delete(device: $device){
          id
          name
        }
      }
    `;
    return this.apollo.use("deviceRecords")
      .mutate<DeviceRecordDelete>({mutation, variables: {device: data}});
  }
}
