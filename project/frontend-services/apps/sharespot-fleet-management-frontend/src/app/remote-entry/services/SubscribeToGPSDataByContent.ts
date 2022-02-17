import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FetchResult} from "@apollo/client/core";
import {FilteredByContentSensorDTO, SensorDTO} from "../dtos/SensorDTO";
import {filter, map} from "rxjs/operators";
import {DeviceLiveDataMapper} from "../mappers/DeviceLiveDataMapper";
import {DeviceData} from "../model/livedata/DeviceData";
import {extract, isNonNull} from "./ObservableFunctions";

@Injectable({
  providedIn: 'root'
})
export class SubscribeToGPSDataByContent {

  constructor(private apollo: Apollo) {
  }

  getData(content: string): Observable<DeviceData> {
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
            status{
              motion
            }
          }
        }
      }
    `;

    return this.apollo.use("fleetManagement").subscribe<FilteredByContentSensorDTO>({query, variables: {content}})
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: FilteredByContentSensorDTO) => DeviceLiveDataMapper.dtoToModel(data.locationByContent))
      );
  }
}