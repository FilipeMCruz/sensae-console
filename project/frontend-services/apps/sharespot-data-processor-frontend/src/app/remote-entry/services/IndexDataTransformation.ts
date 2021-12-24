import {Injectable} from '@angular/core';
import {FetchResult} from '@apollo/client/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {DataTransformationInput} from "../dtos/DataTransformationDTO";

@Injectable({
  providedIn: 'root'
})
export class IndexDataTransformation {

  constructor(private apollo: Apollo) {
  }

  index(data: DataTransformationInput): Observable<FetchResult<DataTransformationInput>> {
    const mutation = gql`
      mutation index($transformation: DataTransformationInput){
        index(transformation: $transformation){
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
      .mutate<DataTransformationInput>({mutation, variables: {transformation: data.index}});
  }
}
