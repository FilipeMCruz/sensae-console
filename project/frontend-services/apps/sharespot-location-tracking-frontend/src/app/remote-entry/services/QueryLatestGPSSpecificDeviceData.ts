import {Apollo, gql} from "apollo-angular";
import {Observable} from "rxjs";
import {FilteredByDeviceGPSSensorLatestData} from "../dtos/SensorDTO";
import {Injectable} from "@angular/core";
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "./ObservableFunctions";
import {DeviceLiveDataMapper} from "../mappers/DeviceLiveDataMapper";
import {DeviceData} from "../model/livedata/DeviceData";

@Injectable({
  providedIn: 'root'
})
export class QueryLatestGPSSpecificDeviceData {

  constructor(private apollo: Apollo) {
  }

  getData(devices: Array<string>): Observable<DeviceData[]> {
    const query = gql`
      query latestByDevice($devices: [String]){
        latestByDevice(devices: $devices){
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

    return this.apollo.use("locationTracking").subscribe<FilteredByDeviceGPSSensorLatestData>({
      query,
      variables: {devices}
    }).pipe(
      map(extract),
      filter(isNonNull),
      map((data: FilteredByDeviceGPSSensorLatestData) => data.latestByDevice.map(s => DeviceLiveDataMapper.dtoToModel(s)))
    );
  }
}
