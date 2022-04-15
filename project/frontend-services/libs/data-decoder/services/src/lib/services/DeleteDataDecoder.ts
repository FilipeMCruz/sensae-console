import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {HttpHeaders} from '@angular/common/http';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {DataDecoderDelete} from "@frontend-services/data-decoder/dto";
import {DataDecoder, SensorTypeId} from "@frontend-services/data-decoder/model";
import {SensorTypeMapper} from "@frontend-services/data-decoder/mapper";

@Injectable({
  providedIn: 'root',
})
export class DeleteDataDecoder {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  delete(event: DataDecoder): Observable<SensorTypeId> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["data_decoders:decoders:delete"]))
      return EMPTY;

    const mutation = gql`
      mutation delete($type: DataTypeInput) {
        delete(type: $type) {
          type
        }
      }
    `;
    return this.apollo
      .use('dataDecoder')
      .mutate<DataDecoderDelete>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: {type: SensorTypeMapper.modelToDto(event.data)},
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: DataDecoderDelete) =>
          SensorTypeMapper.dtoToModel(data.delete)
        )
      );
  }
}
