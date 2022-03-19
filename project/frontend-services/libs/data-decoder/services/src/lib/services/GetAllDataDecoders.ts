import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {Observable} from 'rxjs';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {HttpHeaders} from '@angular/common/http';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from "@frontend-services/core";
import {DataDecoder} from "@frontend-services/data-decoder/model";
import {DataDecoderQuery} from "@frontend-services/data-decoder/dto";
import {DataDecoderQueryMapper} from "@frontend-services/data-decoder/mapper";

@Injectable({
  providedIn: 'root',
})
export class GetAllDataDecoders {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(): Observable<Array<DataDecoder>> {
    const query = gql`
      query decoder {
        decoder {
          data {
            type
          }
          script
        }
      }
    `;
    return this.apollo
      .use('dataDecoder')
      .query<DataDecoderQuery>({
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
        map((data: DataDecoderQuery) =>
          DataDecoderQueryMapper.dtoToModel(data)
        )
      );
  }
}
