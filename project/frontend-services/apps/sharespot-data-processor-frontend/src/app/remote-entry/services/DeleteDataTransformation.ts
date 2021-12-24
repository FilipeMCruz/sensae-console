import {Injectable} from '@angular/core';
import {FetchResult} from '@apollo/client/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {DataTransformationDelete, SensorTypeIdDTO} from "../dtos/DataTransformationDTO";

@Injectable({
  providedIn: 'root'
})
export class DeleteDataTransformation {

  constructor(private apollo: Apollo) {
  }

  delete(data: SensorTypeIdDTO): Observable<FetchResult<DataTransformationDelete>> {
    const mutation = gql`
      mutation delete($type: DataTypeInput){
        delete(type: $type){
          type
        }
      }
    `;
    return this.apollo.use("dataProcessor")
      .mutate<DataTransformationDelete>({mutation, variables: {type: data}});
  }
}
