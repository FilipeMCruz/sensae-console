import { Injectable } from '@angular/core';
import { FetchResult } from '@apollo/client/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { DataTransformationInput } from '../dtos/DataTransformationDTO';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class IndexDataTransformation {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  index(
    data: DataTransformationInput
  ): Observable<FetchResult<DataTransformationInput>> {
    const mutation = gql`
      mutation index($transformation: DataTransformationInput) {
        index(transformation: $transformation) {
          data {
            type
          }
          entries {
            oldPath
            newPath
          }
        }
      }
    `;
    return this.apollo.use('dataProcessor').mutate<DataTransformationInput>({
      mutation,
      context: {
        headers: new HttpHeaders().set(
          'Authorization',
          'Bearer ' + this.auth.getToken()
        ),
      },
      variables: { transformation: data.index },
    });
  }
}
