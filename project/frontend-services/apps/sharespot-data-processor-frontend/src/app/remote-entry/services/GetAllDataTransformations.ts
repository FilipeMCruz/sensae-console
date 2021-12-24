import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {DataTransformationQuery} from '../dtos/DataTransformationDTO';
import {FetchResult} from '@apollo/client/core';

@Injectable({
  providedIn: 'root'
})
export class GetAllDataTransformations {

  constructor(private apollo: Apollo) {
  }

  getData(): Observable<FetchResult<DataTransformationQuery>> {
    const query = gql`
      query transformation{
        transformation{
          data{
            type
          }
          entries{
            oldPath
            newPath
          }
        }
      }
    `;
    return this.apollo.use("dataProcessor")
      .query<DataTransformationQuery>({query, fetchPolicy: "no-cache"});
  }
}
