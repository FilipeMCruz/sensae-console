import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {DeviceRecordQuery} from '../dtos/RecordsDTO';
import {FetchResult} from '@apollo/client/core';
import {HttpHeaders} from "@angular/common/http";
import {AuthService} from "@frontend-services/simple-auth-lib";

@Injectable({
  providedIn: 'root'
})
export class GetAllDeviceRecords {

  constructor(private apollo: Apollo, private auth: AuthService) {
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
      .query<DeviceRecordQuery>({
        query,
        context: {headers: new HttpHeaders().set('Authorization', 'Bearer ' + this.auth.getToken())},
        fetchPolicy: "no-cache"
      });
  }
}
