import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FetchResult} from "@apollo/client/core";
import {GPSSensorLatestData} from "../dtos/SensorDTO";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class QueryLatestGPSDeviceData {

  constructor(private apollo: Apollo) {
  }

  getData(): Observable<FetchResult<GPSSensorLatestData>> {
    const query = gql`
      query latest{
        latest{
          dataId
          device{
            id
            name
            records{
              label
              content
            }
          }
          reportedAt
          data{
            gps{
              longitude
              latitude
            }
          }
        }
      }
    `;

    return this.apollo.use("locationTracking").subscribe<GPSSensorLatestData>({query});
  }
}
