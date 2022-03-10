import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { HttpHeaders } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { extract, isNonNull } from './ObservableFunctions';
import { DataTransformation } from '@frontend-services/data-processor-model';
import { DataTransformationQuery } from '@frontend-services/data-processor-dto';
import { DataTransformationQueryMapper } from '@frontend-services/data-processor-mapper';

@Injectable({
  providedIn: 'root',
})
export class GetAllDataTransformations {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  getData(): Observable<Array<DataTransformation>> {
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
    return this.apollo
      .use('dataProcessor')
      .query<DataTransformationQuery>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        fetchPolicy: 'no-cache',
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: DataTransformationQuery) =>
          DataTransformationQueryMapper.dtoToModel(data)
        )
      );
  }
}
