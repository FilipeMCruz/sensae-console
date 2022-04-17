import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {HttpHeaders} from '@angular/common/http';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {DataTransformation} from '@frontend-services/data-processor/model';
import {DataTransformationRegisterMapper} from '@frontend-services/data-processor/mapper';
import {DataTransformationResult} from '@frontend-services/data-processor/dto';

@Injectable({
  providedIn: 'root',
})
export class IndexDataTransformation {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  index(event: DataTransformation): Observable<DataTransformation> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["data_transformations:transformations:edit"]))
      return EMPTY;

    const mutation = gql`
      mutation index($transformation: DataTransformationInput) {
        index(transformation: $transformation) {
          data {
            type
          }
          entries {
            oldPath
            newPath
            sensorID
          }
        }
      }
    `;
    return this.apollo
      .use('dataProcessor')
      .mutate<DataTransformationResult>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: DataTransformationRegisterMapper.modelToDto(event),
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: DataTransformationResult) =>
          DataTransformationRegisterMapper.dtoToModel(data)
        )
      );
  }
}
