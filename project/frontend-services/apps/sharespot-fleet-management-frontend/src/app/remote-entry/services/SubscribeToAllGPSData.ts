import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {SensorDTO} from "../dtos/SensorDTO";
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "./ObservableFunctions";
import {DeviceLiveDataMapper} from "../mappers/DeviceLiveDataMapper";
import {DeviceData} from "../model/livedata/DeviceData";

@Injectable({
  providedIn: 'root'
})
export class SubscribeToAllGPSData {

  constructor(private apollo: Apollo) {
  }

  getData(): Observable<DeviceData> {
    const query = gql`
      subscription locations{
        locations{
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

    return this.apollo.use("fleetManagement").subscribe<SensorDTO>({query})
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: SensorDTO) => DeviceLiveDataMapper.dtoToModel(data.locations))
      );
  }
}
