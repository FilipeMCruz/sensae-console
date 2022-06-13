import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {IrrigationZone} from "@frontend-services/smart-irrigation/model";
import {
  CreateIrrigationZoneResultDTO,
} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";
import {CreateIrrigationZoneCommand} from "@frontend-services/smart-irrigation/model";

@Injectable({
  providedIn: 'root',
})
export class CreateIrrigationZone {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo() {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["smart_irrigation:garden:create"]);
  }

  execute(command: CreateIrrigationZoneCommand): Observable<IrrigationZone> {
    if (!this.canDo())
      return EMPTY;

    const query = gql`
      mutation createIrrigationZone($instructions: CreateIrrigationZoneCommand){
        createIrrigationZone(instructions: $instructions){
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
      .subscribe<CreateIrrigationZoneResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.createIrrigationZoneInstructions(command)
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: CreateIrrigationZoneResultDTO) => OperationsMapper.createIrrigationZoneDtoToModel(data))
      );
  }
}
