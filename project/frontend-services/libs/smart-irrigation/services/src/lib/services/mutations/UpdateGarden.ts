import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {GardeningArea, UpdateGardeningAreaCommand} from "@frontend-services/smart-irrigation/model";
import {UpdateGardenResultDTO} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";

@Injectable({
  providedIn: 'root',
})
export class UpdateGarden {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  getData(command: UpdateGardeningAreaCommand): Observable<GardeningArea> {
    if (!this.auth.isAuthenticated() || !this.auth.isAllowed(["smart_irrigation:garden:update"]))
      return EMPTY;

    const query = gql`
      mutation updateGarden($instructions: UpdateGardeningAreaCommand){
        updateGarden(instructions: $instructions){
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
      .subscribe<UpdateGardenResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.updateGardenInstructions(command)
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: UpdateGardenResultDTO) => OperationsMapper.updateGardenDtoToModel(data))
      );
  }
}
