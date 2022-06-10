import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {HistoryQueryFilters, SensorDataHistory} from "@frontend-services/smart-irrigation/model";
import {QueryHistoryResultDTO} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";

@Injectable({
  providedIn: 'root',
})
export class FetchHistory {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(filters: HistoryQueryFilters): Observable<Array<SensorDataHistory>> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["smart_irrigation:past_data:read"]))
      return EMPTY;

    const query = gql`
      query history($filters: HistoryQueryFilters){
        history(filters: $filters){
          id
          type
          ledger{
            name
            gps{
              longitude
              latitude
              altitude
            }
            records{
              label
              content
            }
            data{
              id
              reportedAt
              ...on ParkSensorDataHistoryDetails {
                soilMoisture {
                  percentage
                }
                illuminance {
                  lux
                }
              }
              ...on StoveSensorDataHistoryDetails {
                temperature {
                  celsius
                }
                humidity {
                  gramsPerCubicMeter
                }
              }
              ...on ValveDataHistoryDetails {
                valve {
                  status
                }
              }
            }
          }
        }
      }
    `;

    return this.apollo
      .use('smartIrrigation')
      .subscribe<QueryHistoryResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.historyFiltersModelToDto(filters)
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: QueryHistoryResultDTO) => OperationsMapper.fetchHistoryDtoToModel(data))
      );
  }
}
