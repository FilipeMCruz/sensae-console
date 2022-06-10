import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {IrrigationZone, UpdateIrrigationZoneCommand} from "@frontend-services/smart-irrigation/model";
import {UpdateIrrigationZoneResultDTO} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";

@Injectable({
  providedIn: 'root',
})
export class UpdateIrrigationZone {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo() {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["smart_irrigation:garden:edit"]);
  }

  execute(command: UpdateIrrigationZoneCommand): Observable<IrrigationZone> {
    if (!this.canDo())
      return EMPTY;

    const query = gql`
      mutation updateIrrigationZone($instructions: UpdateIrrigationZoneCommand){
        updateIrrigationZone(instructions: $instructions){
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
      .subscribe<UpdateIrrigationZoneResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.updateIrrigationZoneInstructions(command)
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: UpdateIrrigationZoneResultDTO) => OperationsMapper.updateIrrigationZoneDtoToModel(data))
      );
  }
}
