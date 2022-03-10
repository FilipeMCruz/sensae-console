import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { HttpHeaders } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { extract, isNonNull } from "@frontend-services/core";
import {
  DataTransformation,
  SensorTypeId,
} from '@frontend-services/data-processor-model';
import { DataTransformationDelete } from '@frontend-services/data-processor-dto';
import { SensorTypeMapper } from '@frontend-services/data-processor-mapper';

@Injectable({
  providedIn: 'root',
})
export class DeleteDataTransformation {
  constructor(private apollo: Apollo, private auth: AuthService) {}

  delete(event: DataTransformation): Observable<SensorTypeId> {
    const mutation = gql`
      mutation delete($type: DataTypeInput) {
        delete(type: $type) {
          type
        }
      }
    `;
    return this.apollo
      .use('dataProcessor')
      .mutate<DataTransformationDelete>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: { type: SensorTypeMapper.modelToDto(event.data) },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: DataTransformationDelete) =>
          SensorTypeMapper.dtoToModel(data.delete)
        )
      );
  }
}
