import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FetchResult} from "@apollo/client/core";
import {SensorDTO} from "../dtos/SensorDTO";

@Injectable({
  providedIn: 'root'
})
export class SubscribeToGPSDataByContent {

  constructor(private apollo: Apollo) {
  }

  getData(content: string): Observable<FetchResult<SensorDTO>> {
    const query = gql`
      subscription locationByContent($content: String){
        locationByContent(content: $content){
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

    return this.apollo.use("locationTracking").subscribe<SensorDTO>({query, variables: {content}});
  }
}
