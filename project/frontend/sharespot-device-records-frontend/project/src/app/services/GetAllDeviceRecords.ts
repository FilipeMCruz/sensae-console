import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {DeviceRecordQuery} from "../dtos/RecordsDTO";
import {ApolloQueryResult} from "@apollo/client/core";

@Injectable({
  providedIn: 'root'
})
export class GetAllDeviceRecords {

  constructor(private apollo: Apollo) {
  }

  getData(): Observable<ApolloQueryResult<DeviceRecordQuery>> {
    let query = gql`
      query deviceRecords{
        deviceRecords{
          deviceId
          entries{
            label
            content
            type
          }
        }
      }
    `;
    return this.apollo
      .query<DeviceRecordQuery>({query: query});
  }
}
