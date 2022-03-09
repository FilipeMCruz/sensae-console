import { Injectable } from '@angular/core';
import { FetchResult } from '@apollo/client/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import {
  DataTransformationDelete,
  SensorTypeIdDTO,
} from '../dtos/DataTransformationDTO';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class DeleteDataTransformation {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  delete(
    data: SensorTypeIdDTO
  ): Observable<FetchResult<DataTransformationDelete>> {
    const mutation = gql`
      mutation delete($type: DataTypeInput) {
        delete(type: $type) {
          type
        }
      }
    `;
    return this.apollo.use('dataProcessor').mutate<DataTransformationDelete>({
      mutation,
      context: {
        headers: new HttpHeaders().set(
          'Authorization',
          'Bearer ' + this.auth.getToken()
        ),
      },
      variables: { type: data },
    });
  }
}
