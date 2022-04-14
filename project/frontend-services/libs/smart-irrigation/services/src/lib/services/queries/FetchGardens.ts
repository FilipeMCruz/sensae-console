import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {GardeningArea} from "@frontend-services/smart-irrigation/model";
import {QueryGardensResultDTO} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";

@Injectable({
  providedIn: 'root',
})
export class FetchGardens {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(): Observable<Array<GardeningArea>> {
    const query = gql`
      query fetchGardens{
        fetchGardens{
          id
          name
          area{
            position
            longitude
            latitude
            altitude
          }
        }
      }
    `;

    return this.apollo
      .use('smartIrrigation')
      .subscribe<QueryGardensResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: QueryGardensResultDTO) => OperationsMapper.fetchGardenDtoToModel(data))
      );
  }
}
