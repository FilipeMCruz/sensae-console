import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {HttpHeaders} from '@angular/common/http';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {DataTransformation} from '@frontend-services/data-processor/model';
import {DataTransformationRegisterMapper} from '@frontend-services/data-processor/mapper';
import {DataTransformationInput} from '@frontend-services/data-processor/dto';

@Injectable({
  providedIn: 'root',
})
export class IndexDataTransformation {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  index(event: DataTransformation): Observable<DataTransformation> {
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
    return this.apollo
      .use('dataProcessor')
      .mutate<DataTransformationInput>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: {
          transformation: DataTransformationRegisterMapper.modelToDto(event),
        },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: DataTransformationInput) =>
          DataTransformationRegisterMapper.dtoToModel(data)
        )
      );
  }
}
