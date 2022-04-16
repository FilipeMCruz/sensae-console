import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {GardeningArea} from "@frontend-services/smart-irrigation/model";
import {
  CreateGardenResultDTO,
} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";
import {CreateGardeningAreaCommand} from "@frontend-services/smart-irrigation/model";

@Injectable({
  providedIn: 'root',
})
export class CreateGarden {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  execute(command: CreateGardeningAreaCommand): Observable<GardeningArea> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["smart_irrigation:garden:create"]))
      return EMPTY;

    const query = gql`
      mutation createGarden($instructions: CreateGardeningAreaCommand){
        createGarden(instructions: $instructions){
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
      .subscribe<CreateGardenResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.createGardenInstructions(command)
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: CreateGardenResultDTO) => OperationsMapper.createGardenDtoToModel(data))
      );
  }
}
