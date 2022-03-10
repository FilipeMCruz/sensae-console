import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable } from 'rxjs';
import { DataTransformationDelete } from '../dtos/DataTransformationDTO';
import { AuthService } from '@frontend-services/simple-auth-lib';
import { HttpHeaders } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import { SensorTypeMapper } from '../mapper/SensorTypeMapper';
import { SensorTypeId } from '../model/SensorTypeId';
import { extract, isNonNull } from './ObservableFunctions';
import { DataTransformation } from '../model/DataTransformation';

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
