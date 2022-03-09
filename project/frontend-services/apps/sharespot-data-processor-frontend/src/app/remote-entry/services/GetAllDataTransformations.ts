import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { DataTransformationQuery } from '../dtos/DataTransformationDTO';
import { FetchResult } from '@apollo/client/core';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class GetAllDataTransformations {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  getData(): Observable<FetchResult<DataTransformationQuery>> {
    const query = gql`
      query transformation {
        transformation {
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
    return this.apollo.use('dataProcessor').query<DataTransformationQuery>({
      query,
      context: {
        headers: new HttpHeaders().set(
          'Authorization',
          'Bearer ' + this.auth.getToken()
        ),
      },
      fetchPolicy: 'no-cache',
    });
  }
}
