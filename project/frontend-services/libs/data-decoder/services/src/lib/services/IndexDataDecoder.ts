import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {HttpHeaders} from '@angular/common/http';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {DataDecoder} from "@frontend-services/data-decoder/model";
import {DataDecoderRegisterMapper} from "@frontend-services/data-decoder/mapper";
import {DataDecoderResult} from "@frontend-services/data-decoder/dto";

@Injectable({
  providedIn: 'root',
})
export class IndexDataDecoder {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  index(event: DataDecoder): Observable<DataDecoder> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["data_decoders:decoders:edit"]))
      return EMPTY;

    const mutation = gql`
      mutation index($decoder: DataDecoderInput) {
        index(decoder: $decoder) {
          data {
            type
          }
          script
          lastTimeSeen
        }
      }
    `;
    return this.apollo
      .use('dataDecoder')
      .mutate<DataDecoderResult>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: DataDecoderRegisterMapper.modelToDto(event),
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: DataDecoderResult) =>
          DataDecoderRegisterMapper.dtoToModel(data)
        )
      );
  }
}
