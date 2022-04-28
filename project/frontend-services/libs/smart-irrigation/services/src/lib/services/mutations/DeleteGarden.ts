import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {
  DeleteGardeningAreaCommand,
  GardeningArea,
} from "@frontend-services/smart-irrigation/model";
import {DeleteGardenResultDTO} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";

@Injectable({
  providedIn: 'root',
})
export class DeleteGarden {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(command: DeleteGardeningAreaCommand): Observable<GardeningArea> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["smart_irrigation:garden:delete"]))
      return EMPTY;

    const query = gql`
      mutation deleteGarden($instructions: DeleteGardeningAreaCommand){
        deleteGarden(instructions: $instructions){
          id
          name
          area{
            position
            longitude
            latitude
            altitude
          }
        }
      }
    `;

    return this.apollo
      .use('smartIrrigation')
      .subscribe<DeleteGardenResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.deleteGardenInstructions(command)
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: DeleteGardenResultDTO) => OperationsMapper.deleteGardenDtoToModel(data))
      );
  }
}
